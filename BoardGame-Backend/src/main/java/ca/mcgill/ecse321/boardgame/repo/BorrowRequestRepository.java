package ca.mcgill.ecse321.boardgame.repo;

import ca.mcgill.ecse321.boardgame.model.BorrowRequest;
import org.springframework.data.repository.CrudRepository;

public interface BorrowRequestRepository extends CrudRepository<BorrowRequest,Long> {
    public BorrowRequest findBorrowRequestById(long id);
}
