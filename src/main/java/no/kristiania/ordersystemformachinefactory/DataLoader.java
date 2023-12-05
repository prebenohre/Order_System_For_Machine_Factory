package no.kristiania.ordersystemformachinefactory;

import com.github.javafaker.Faker;
import no.kristiania.ordersystemformachinefactory.model.*;
import no.kristiania.ordersystemformachinefactory.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.*;

@Configuration
public class DataLoader {

    @Bean
    @Profile("dev")
    CommandLineRunner initDatabase(AddressRepository addressRepository,
                                   CustomerRepository customerRepository,
                                   MachineRepository machineRepository,
                                   OrderRepository orderRepository,
                                   PartRepository partRepository,
                                   SubassemblyRepository subassemblyRepository) {
        return args -> {
            Faker faker = new Faker();

            // Generer og lagre adresser
            Set<Address> addresses = new HashSet<>();
            for (int i = 0; i < 50; i++) {
                Address address = new Address(
                        faker.address().buildingNumber(),
                        faker.address().streetName(),
                        faker.address().city(),
                        faker.address().zipCode(),
                        faker.address().country());
                addresses.add(addressRepository.save(address));
            }

            // Generer og lagre kunder
            List<Address> addressList = new ArrayList<>(addresses); // Konverter settet til en liste for enkel tilgang
            for (int i = 0; i < 50; i++) {
                Customer customer = new Customer(
                        faker.name().fullName(),
                        faker.internet().emailAddress());

                // Velg en tilfeldig adresse fra listen og sett den for kunden
                Address randomAddress = addressList.get(faker.random().nextInt(addressList.size()));
                Set<Address> customerAddresses = new HashSet<>();
                customerAddresses.add(randomAddress);
                customer.setAddresses(customerAddresses);

                customerRepository.save(customer);
            }

            // Generer og lagre maskiner, undermonteringer og deler
            for (int i = 0; i < 50; i++) {
                Machine machine = new Machine(
                        faker.commerce().productName(),
                        faker.company().name());
                machine = machineRepository.save(machine);

                for (int j = 0; j < 3; j++) {
                    Subassembly subassembly = new Subassembly(
                            faker.lorem().word());
                    subassembly.setMachine(machine);
                    subassembly = subassemblyRepository.save(subassembly);

                    for (int k = 0; k < 5; k++) {
                        Part part = new Part(
                                faker.commerce().productName(),
                                faker.company().name(),
                                faker.lorem().sentence());
                        part.setSubassembly(subassembly); // Tilordne delen til en undermontering
                        partRepository.save(part);
                    }
                }
            }

            // Generer og lagre ordre med maskiner
            List<Customer> customers = customerRepository.findAll();
            List<Machine> machines = machineRepository.findAll();
            for (int i = 0; i < customers.size(); i++) {
                Order order = new Order(new Date());
                order.setCustomer(customers.get(i % customers.size()));

                // Velg et tilfeldig sett med maskiner og tilordne dem til ordren
                Set<Machine> orderMachines = new HashSet<>();
                for (int j = 0; j < faker.random().nextInt(1, machines.size()); j++) {
                    orderMachines.add(machines.get(faker.random().nextInt(machines.size())));
                }
                order.setMachines(orderMachines);

                orderRepository.save(order);
            }
        };
    }
}
