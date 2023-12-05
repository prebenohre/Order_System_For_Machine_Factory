package no.kristiania.ordersystemformachinefactory.repository;

import no.kristiania.ordersystemformachinefactory.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    // Her kan du legge til eventuelle egendefinerte metoder
}