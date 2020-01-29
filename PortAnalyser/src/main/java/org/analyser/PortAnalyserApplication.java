package org.analyser;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.analyser.dao.AppRoleRepository;
import org.analyser.dao.PersonRepository;
import org.analyser.entities.AppRole;
import org.analyser.entities.AppUser;
import org.analyser.entities.Person;
import org.analyser.entities.SystemServiceBenchmark;
import org.analyser.security.SecurityConstants;
import org.analyser.services.Implementations.HostScannerImpl;
import org.analyser.services.Implementations.LANScannerImpl;
import org.analyser.services.interfaces.IAccountService;
import org.analyser.services.interfaces.IScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PortAnalyserApplication implements CommandLineRunner{
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IScannerService scannerService;
	
	public static void main(String[] args) {
		SpringApplication.run(PortAnalyserApplication.class, args);
	}

	/**
	 * Quand l'application va demarrer, toutes les methodes avec l'annotation @Bean seront executees
	 * Et le resultat retournE devient un BEAN
	 * C'est a dire qu'on peut injecter partout dans l'application
	 */
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(); // retourne une instance vide de BCryptPasswordEncoder
	}
	
	@Override
	public void run(String... args) throws Exception {
		// Personnes 
		Person sitraka = new Person();
		sitraka.setFirstname("Mamy");
		sitraka.setLastname("Razafindrakoto");
		sitraka.setEmail("sitrakarazafi24@gmail.com");
		sitraka.setAddress("11 rue des Freres Lumiere 68350 Brunstatt");
		sitraka.setTelephone("0766121813");
		
		Person angele = new Person();
		angele.setFirstname("Angele");
		angele.setLastname("Mbayang");
		angele.setEmail("seckmbayang24@gmail.com");
		angele.setAddress("13 rue des Freres Lumiere 68350 Brunstatt");
		angele.setTelephone("0865320300");
		
		Person safidy = new Person();
		safidy.setFirstname("Safidy");
		safidy.setLastname("Razafindrakoto");
		safidy.setEmail("safidy@gmail.com");
		safidy.setAddress("20 avenue d'independance 65200 Colmar");
		safidy.setTelephone("0612154879");
		
		Person michael = new Person();
		michael.setFirstname("Michael");
		michael.setLastname("Henri");
		michael.setEmail("michael@gmail.com");
		michael.setAddress("30 rue d'Ivry 92 120 Ivry");
		michael.setTelephone("0812160031");
		
		personRepository.save(sitraka);
		personRepository.save(angele);
		personRepository.save(safidy);
		personRepository.save(michael);

		// Roles
		AppRole admin = new AppRole(SecurityConstants.ADMIN_ROLE);
		AppRole user = new AppRole(SecurityConstants.USER_ROLE);

		appRoleRepository.save(admin);
		appRoleRepository.save(user);

		// Users
		AppUser sitrakaAccount = new AppUser();
		sitrakaAccount.setUsername(sitraka.getEmail());
		sitrakaAccount.setPassword("admin");
		sitrakaAccount.setPerson(sitraka);
		
		AppUser angeleAccount = new AppUser();
		angeleAccount.setUsername(angele.getEmail());
		angeleAccount.setPassword("user");
		angeleAccount.setPerson(angele);
		
		accountService.saveUser(sitrakaAccount);
		accountService.saveUser(angeleAccount);

		// Assigner des roles a un user
		accountService.addRoleToUser(sitraka.getEmail(), SecurityConstants.ADMIN_ROLE);
		accountService.addRoleToUser(angele.getEmail(), SecurityConstants.USER_ROLE);

		//System.out.println(sitraka.getEmail());
		personRepository.findAll().forEach(person -> System.out.println(person.getFirstname()));
		
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("Wake-on-LAN", 9, "2.2", false, true, true, false, true, true, "Wake-on-LAN est fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("FTP données", 20, "2.2", true, true, true, false, true, true, "FTP tranfert de données est fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("FTP commandes", 21, "2.2", true, true, false, false, true, true, "FTP commandes est fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("SSH", 22, "2.2", true, true, true, false, true, true, "Ce 22 port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("Telnet", 23, "2.2", true, true, true, false, true, true, "Ce 23 port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("SMTP", 25, "2.2", true, true, true, true, false, false, "Le port 25 de SMTP doit être ouvert car c'est un port de messagerie pour recevoir des courriels."));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("IP : DNS", 53, "2.2", true, true, true, false, true, true, "Ce port 53 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("DHCP", 67, "2.2", true, true, true, false, true, true, "Ce port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("bootpc", 68, "2.2", true, true, true, false, true, true, "Ce port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("HTTP Protocole", 80, "2.2", false, true, true, false, true, true, "Port 80 du protocole HTTP doit être fermé"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("POP - récuperation courriel", 110, "2.2", false, true, true, false, true, true, "Ce port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("Network Time Protocol (NTP)", 123, "2.2", false, true, true, false, true, true, "Ce port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("IMAP", 143, "2.2", false, true, true, false, true, true, "Ce port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("LDAP", 389, "2.2", true, true, true, false, true, true, "Ce port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("HTTPS", 443, "2.2", true, true, true, false, true, true, "Ce port 443 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("SMTPS", 465, "2.2", true, true, true, false, true, true, "Ce port 465 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("IPsec", 500, "2.2", true, true, true, false, true, true, "Ce port 500 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("RTSPT", 554, "2.2", false, true, true, false, true, true, "Ce port 554 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("LDAP sécurisé par une couche SSL/TLS", 636, "2.2", true, true, true, false, true, true, "Ce port 636 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("Lotus Notes Domino", 1352, "2.2", false, true, true, false, true, true, "Ce 1352 port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("MS SQL", 1433, "2.2", true, true, true, false, true, true, "Ce port 1433 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("VPN PPTP", 1723, "2.2", false, true, true, false, true, true, "Ce port 1723 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("MySQL", 3308, "2.2", true, true, true, false, true, true, "C'est un serveur base de données qu'il faut protéger"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("MariaDB", 3306, "2.2", true, true, true, false, true, true, "C'est un serveur base de données qu'il faut protéger"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("Spring - port par défaut", 8080, "2.2", true, true, true, false, true, true, "Port 8080 de Spring ne doit pas être accéssible si ce n'est pas un serveur web"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("PostgreSQL", 5432, "2.2", true, true, true, false, true, true, "C'est un serveur base de données qu'il faut protéger"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("RDP", 3389, "2.2", false, true, true, false, true, true, "Ce port 3389 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("IRC", 6667, "2.2", false, true, true, false, true, true, "Ce port 6667 doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("Microsoft-DS Active Directory78, Windows shares ", 445, "2.2", true, true, false, false, true, true, "Ce 445 port doit être fermé par défaut. C'est à configurer"));
		scannerService.saveSystemServiceForBenchmark(new SystemServiceBenchmark("Telnet protocol over TLS/SSL", 992, "2.2", true, true, true, false, true, true, "Ce 992 port doit être fermé par défaut. C'est à configurer"));
		
		/*
		LANScannerImpl s = new LANScannerImpl();
		List<InetAddress> l = s.scan("127.0.0.0", "127.0.0.2");
		
		HostScannerImpl hs = new HostScannerImpl(8080, 8088, 0);
		hs.scan(l);
		//for (Map.Entry mapentry : hs.scan(l).entrySet()) {
	          // System.out.println("clé: "+mapentry.getKey());
	           
	         //  for(Integer v :  (List<Integer>) mapentry.getValue()) {
	        	//   System.out.println("Nombre port ouvert: " + hs.scan(l).entrySet().size());
	        	//   System.out.println("Port ouvert: " + v);
	         //  }
	   //  }
		
		System.out.println("NOMBRE HOTES TROUVES: " + s.getIpFound());*/
	}
}
