package org.analyser.web;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.analyser.entities.AppUser;
import org.analyser.entities.IPAddress;
import org.analyser.entities.Scan;
import org.analyser.models.ScanInfoForm;
import org.analyser.entities.SystemService;
import org.analyser.security.SessionHandler;
import org.analyser.services.Implementations.HostScannerImpl;
import org.analyser.services.Implementations.LANScannerImpl;
import org.analyser.services.interfaces.IAccountService;
import org.analyser.services.interfaces.IScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.IncompatibleAddressException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ua_parser.Client;
import ua_parser.Parser;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IScannerService scannerService;
		
	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(required = false) Long id, HttpSession session, Model model) throws IOException {
	//	public String home(HttpServletRequest request,@RequestParam(required = false) Long id, HttpSession session, Model model) throws IOException {
		AppUser user = accountService.findUserById(id);
		if (id != null) {
			if (user == null) {
				return "redirect:/login";
			}
			SessionHandler.addUser(session, user);
		}

		if (SessionHandler.getUser(session) == null) {
			return "redirect:/login";
		}
		/*
        String userAgent = request.getHeader("User-Agent");
        Parser uaParser = new Parser();
        Client c = uaParser.parse(userAgent);
        String systeme = "Système d'exploitation : " + c.os.family + " " + c.os.major + "\nNavigateur : ";
        String version = c.userAgent.major + (c.userAgent.minor != null ? "." + c.userAgent.minor : "") + (c.userAgent.patch != null ? "." + c.userAgent.patch : "");
        systeme+= c.userAgent.family+" "+version;
        switch (c.os.family) {
            case "Windows":
                switch (c.userAgent.family) {
                    case "Chrome":
                        URL url = new URL("https://en.wikipedia.org/wiki/Google_Chrome_version_history");
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                            String line;
                            StringBuilder sb = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                            }
                            Pattern p = Pattern.compile("<td style=\"white-space:nowrap; background:#a0e75a;\">(.*)");
                            Matcher m = p.matcher(sb);
                            m.find();
                            String stable = m.group(1);
                            if (stable.equals(version)) {
                                systeme += "\nVotre navigateur est à jour";
                            } else {
                                systeme += "\nVotre navigateur n'est pas à jour";
                            }
                        }
                        break;
                    default:
                }
                break;
            default:
        }
        System.out.println(request.getRemoteAddr());
        model.addAttribute("systeme", systeme);
		*/
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());
		model.addAttribute("LastSessionDate", dateFormat.format(date));

		setUserInfoToModel(session, model);
		model.addAttribute("scanModel", new ScanInfoForm("127.0.0.0", "127.0.0.1", 0, 0));
		model.addAttribute("scanResult", new Scan());
		model.addAttribute("post", false);
		return "/user/home";
	}
	
	@PostMapping("/scan")
	public String scan(@RequestParam(required = false) Long id, @Valid ScanInfoForm form, HttpServletRequest request, HttpSession session, BindingResult br, Model model) throws UnknownHostException, AddressStringException, IncompatibleAddressException {
		AppUser user = accountService.findUserById(id);
		System.out.println("Nom Addresse IP du client: " + request.getRemoteHost());
		System.out.println("Adresse IP du client: " + request.getRemoteAddr());
		System.out.println("Port de la requete client: " + request.getRemotePort());
		System.out.println("User: " + request.getRemoteUser());
		System.out.println("Adresse IP client depuis l'entete http: " + request.getHeader("X-FORWARDED-FOR"));
		if (id != null) {
			if (user == null) {
				return "redirect:/login";
			}
			SessionHandler.addUser(session, user);
		}

		if (SessionHandler.getUser(session) == null) {
			return "redirect:/login";
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());
		model.addAttribute("LastSessionDate", dateFormat.format(date));

		setUserInfoToModel(session, model);
		model.addAttribute("scanModel", form);
		
		LANScannerImpl s = new LANScannerImpl();
		List<InetAddress> ls = s.scan("127.0.0.1", "127.0.0.1");
		
		HostScannerImpl hs = new HostScannerImpl(form.getPortStart(), form.getPortEnd(), 0);
		hs.scan(ls);
		// Le resultat du scan
		Scan scan = hs.getScan();
		scan.setUser(SessionHandler.getUser(session)); // On associe a un utilisateur
		Scan scanSaved = scannerService.saveScan(scan); // On enregistre et on recupère le scan enregistré
		
		// Enregistrement de toutes les adresses IP du scan
		List<IPAddress> ipAddresses = (List<IPAddress>) scan.getIps();
		for(IPAddress ip : ipAddresses) {
			ip.setScan(scanSaved);
			scannerService.saveIPAddress(ip);
			
			// Pour chaque adresse IP, on enregistre tous les ports pour chaque adresse
			
			for(SystemService service : (List<SystemService>) ip.getServices()) {
				scannerService.saveSystemService(service);
			}
		}
		model.addAttribute("post", true);
		model.addAttribute("scanResult", scan);
		model.addAttribute("lanInfos", s);
		model.addAttribute("scanInfos", hs);
		return "/user/home";
	}
	
	private void setUserInfoToModel(HttpSession session, Model model) {
		model.addAttribute("email", SessionHandler.getUser(session).getUsername());
		model.addAttribute("fullname", SessionHandler.getUser(session).getPerson().getFirstname() + " "
				+ SessionHandler.getUser(session).getPerson().getLastname());
	}
}
