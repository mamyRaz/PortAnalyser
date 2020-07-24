package org.analyser.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.analyser.entities.AppUser;
import org.analyser.entities.IPAddress;
import org.analyser.entities.Person;
import org.analyser.entities.Scan;
import org.analyser.models.ScanInfoForm;
import org.analyser.models.UserRegistrationForm;
import org.analyser.entities.SessionInfo;
import org.analyser.entities.SystemService;
import org.analyser.entities.SystemServiceBenchmark;
import org.analyser.security.SessionHandler;
import org.analyser.services.Implementations.HostScannerImpl;
import org.analyser.services.Implementations.LANScannerImpl;
import org.analyser.services.interfaces.IAccountService;
import org.analyser.services.interfaces.IScannerService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua_parser.Client;
import ua_parser.Parser;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IScannerService scannerService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(@RequestParam(required = false) Long id, HttpSession session, Model model) throws IOException {
    //public String home(HttpServletRequest request, @RequestParam(required = false) Long id, HttpSession session, Model model) throws IOException {
        /**
         * Si l'utilisateur tape /admin dans ce cas, on n'a pas besoin de "id"
         * s'il est deja authentifié Mais normalement il doit avoir une session
         * active dans laquelle on trouve l'objet utilisateur sinon reconnexion
         * L'id sur l'URL est utile pour la premiere connexion afin
         * d'initialiser la session
         */
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
        model.addAttribute("scanModel", new ScanInfoForm());
        model.addAttribute("scanResult", new Scan());
        model.addAttribute("post", false);
        return "/admin/home";
    }

    @GetMapping("/users")
    public String getUsersList(HttpSession session, Model model) {
        setUserInfoToModel(session, model);
        List<AppUser> users = accountService.findAllUsers();
        model.addAttribute("userCount", users.size());
        model.addAttribute("users", users);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());

        model.addAttribute("LastSessionDate", dateFormat.format(date));
        return "admin/users_list";

    }

    @GetMapping("/users/add")
    public String addUser(@RequestParam(required = false, name = "choice") Long choice, @RequestParam(required = false, name = "id") Long id, HttpSession session,
            Model model) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());
        model.addAttribute("LastSessionDate", dateFormat.format(date));
        setUserInfoToModel(session, model);

        if (SessionHandler.getUser(session) == null) {
            return "redirect:/login";
        }

        if (choice != null) {
            if (choice == 1) {
                Person person = accountService.findPersonById(id);

                if (person == null) {
                    return "/admin/users_add";
                }

                UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
                userRegistrationForm.setLastname(person.getLastname());
                userRegistrationForm.setFirstname(person.getFirstname());
                userRegistrationForm.setEmail(person.getEmail());
                userRegistrationForm.setAddress(person.getAddress());
                userRegistrationForm.setTelephone(person.getTelephone());

                model.addAttribute("persons", accountService.findAllPersons());
                model.addAttribute("userRegistrationForm", userRegistrationForm);
                model.addAttribute("roles", accountService.findAllRoles());
                return "/admin/users_add_as_exist_person";
            } else if (choice == 2) {
                model.addAttribute("userRegistrationForm", new UserRegistrationForm());
                model.addAttribute("roles", accountService.findAllRoles());
                return "/admin/users_add_as_new_person";
            }
        }
        return "/admin/users_add";
    }

    @PostMapping("/users/add")
    public String test(@RequestParam(required = true, name = "choice") Long choice,
            @Valid @ModelAttribute("userRegistrationForm") UserRegistrationForm userRegistrationForm, BindingResult br,
            HttpSession session, Model model) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());
        model.addAttribute("LastSessionDate", dateFormat.format(date));
        setUserInfoToModel(session, model);

        if (SessionHandler.getUser(session) == null) {
            return "redirect:/login";
        }

        AppUser user;
        if (choice == null) {

        }

        if (choice == 1) {
            user = new AppUser();
        } else if (choice == 2) {
            if (br.hasErrors()) {
                for (ObjectError err : br.getAllErrors()) {
                    System.out.println("Erreur: " + err);
                }
                model.addAttribute("roles", accountService.findAllRoles());
                return "/admin/users_add_as_new_person";
            }
            Person person = new Person(userRegistrationForm.getLastname(), userRegistrationForm.getFirstname(),
                    userRegistrationForm.getEmail(), userRegistrationForm.getAddress(),
                    userRegistrationForm.getTelephone());
            user = new AppUser();
            user.setUsername(userRegistrationForm.getEmail());
            user.setPassword(userRegistrationForm.getPassword());
            user.setRoles(userRegistrationForm.getRoles());
            user.setPerson(person);
        } else {
            return "/admin/user_add";
        }

        System.out.println("Ajout avec succes");
        accountService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/details")
    public String userDetails(@RequestParam(required = true) Long id, HttpSession session, Model model) {
        if (SessionHandler.getUser(session) == null) {
            return "redirect:/login";
        }
        AppUser user = SessionHandler.getUser(session);
        System.out.println("Date de derniere connexion: "
                + accountService.findLastSession(SessionHandler.getUser(session).getId()));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());
        model.addAttribute("LastSessionDate", dateFormat.format(date));

        AppUser userDetails = accountService.findUserById(id);
        if (userDetails == null) {
            model.addAttribute("sessionInfo", new ArrayList<SessionInfo>());
        } else {
            model.addAttribute("sessionInfo", accountService.findAllSessionInfo(userDetails));
        }

        model.addAttribute("user", userDetails);
        setUserInfoToModel(session, model);
        return "/admin/users_details";
    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        accountService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/scan")
    public String scan(@RequestParam(required = false) Long id, @Valid ScanInfoForm form, HttpSession session,
            BindingResult br, Model model) {
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

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());
        model.addAttribute("LastSessionDate", dateFormat.format(date));

        setUserInfoToModel(session, model);
        model.addAttribute("scanModel", form);

        LANScannerImpl s = new LANScannerImpl();
        List<InetAddress> ls = s.scan(form.getIpStart(), form.getIpEnd());

        HostScannerImpl hs = new HostScannerImpl(form.getPortStart(), form.getPortEnd(), 0);
        hs.scan(ls);
        // Le resultat du scan
        Scan scan = hs.getScan();
        scan.setUser(SessionHandler.getUser(session)); // On associe a un utilisateur
        Scan scanSaved = scannerService.saveScan(scan); // On enregistre et on recupère le scan enregistré

        List<String> alerts = new ArrayList<String>();

        // Enregistrement de toutes les adresses IP du scan
        List<IPAddress> ipAddresses = (List<IPAddress>) scan.getIps();
        for (IPAddress ip : ipAddresses) {
            ip.setScan(scanSaved);
            scannerService.saveIPAddress(ip);

            // Pour chaque adresse IP, on enregistre tous les ports pour chaque adresse
            for (SystemService service : (List<SystemService>) ip.getServices()) {

                if (scannerService.findSystemServiceForBenchmarkByPort(service.getPort()) != null) {
                    SystemServiceBenchmark bench = scannerService
                            .findSystemServiceForBenchmarkByPort(service.getPort());
                    if (bench.isPortOpenedNormal() == service.isPortClosed()) {
                        alerts.add(bench.getReasonOfAbnormal());
                    }
                }

                scannerService.saveSystemService(service);
            }
        }

        boolean hasAlert = !alerts.isEmpty();
        model.addAttribute("post", true);
        model.addAttribute("scanResult", scan);
        model.addAttribute("lanInfos", s);
        model.addAttribute("scanInfos", hs);
        model.addAttribute("alerts", alerts);
        model.addAttribute("hasAlert", hasAlert);
        return "/admin/home";
    }

    @GetMapping("/logs")
    public String getLogsList(HttpSession session, Model model) {
        setUserInfoToModel(session, model);
        List<Scan> scans = scannerService.getAllScans();
        model.addAttribute("scans", scans);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());

        model.addAttribute("LastSessionDate", dateFormat.format(date));
        return "/admin/logs_list";
    }

    @GetMapping("/logs/details")
    public String scanDetails(@RequestParam(required = true) Long id, HttpSession session, Model model) {
        if (SessionHandler.getUser(session) == null) {
            return "redirect:/login";
        }
        AppUser user = SessionHandler.getUser(session);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = accountService.findLastSession(SessionHandler.getUser(session).getId());
        model.addAttribute("LastSessionDate", dateFormat.format(date));

        Scan scan = scannerService.getScanById(id);
        List<IPAddress> ipAddresses = scannerService.getIPAddressByScanId(id);

        model.addAttribute("hosts", ipAddresses.size());
        model.addAttribute("scan", scan);
        setUserInfoToModel(session, model);
        return "/admin/logs_details";
    }

    private void setUserInfoToModel(HttpSession session, Model model) {
        model.addAttribute("email", SessionHandler.getUser(session).getUsername());
        model.addAttribute("fullname", SessionHandler.getUser(session).getPerson().getFirstname() + " "
                + SessionHandler.getUser(session).getPerson().getLastname());
    }
}
