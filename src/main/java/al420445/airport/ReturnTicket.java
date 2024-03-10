package al420445.airport;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("R")
public class ReturnTicket extends Ticket {
    private LocalDate latestReturnDate;
    public LocalDate getLatestReturnDate() {
        return latestReturnDate;
    }

    public void setLatestReturnDate(LocalDate latestReturnDate) {
        this.latestReturnDate = latestReturnDate;
    }

}
