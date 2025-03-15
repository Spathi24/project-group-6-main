package ca.mcgill.ecse321.boardgame.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

import java.sql.*;

@Entity(name = "BorrowRequest")
public class BorrowRequest {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Temporal(TemporalType.DATE)
    private Date requestDate;

    @Temporal(TemporalType.DATE)
    private Date decisionDate;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserAccount borrower;

    @ManyToOne
    @JoinColumns({@JoinColumn(nullable = false),@JoinColumn(nullable = false )})
    private GameCopy gameToBorrow;

    protected BorrowRequest(){}

    public BorrowRequest(RequestStatus status,Date requestDate,Date decisionDate,Date startDate,Date endDate,UserAccount borrower,GameCopy gameToBorrow){
        this.status = status;
        this.requestDate = requestDate;
        this.decisionDate = decisionDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.borrower = borrower;
        this.gameToBorrow = gameToBorrow;
    }

    public long getId(){
        return id;
    }

    public RequestStatus getRequestStatus(){
        return status;
    }

    public Date getRequestDate(){
        return requestDate;
    }

    public Date getDecisionDate(){
        return decisionDate;
    }

    public Date getStartDate(){
        return startDate;
    }

    public Date getEndDate(){
        return endDate;
    }

    public UserAccount getBorrower(){
        return borrower;
    }

    public GameCopy getGameToBorrow(){
        return gameToBorrow;
    }


}
