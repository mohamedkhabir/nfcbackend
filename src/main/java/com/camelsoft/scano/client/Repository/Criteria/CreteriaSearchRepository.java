package com.camelsoft.scano.client.Repository.Criteria;


import com.camelsoft.scano.client.Enum.requestState;
import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.Enum.step;
import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.company_request;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.tools.exception.NotFoundException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class CreteriaSearchRepository {
    private final EntityManager em;
    private final CriteriaBuilder criteriaBuilder;

    public CreteriaSearchRepository(EntityManager em) {
        this.em = em;
        this.criteriaBuilder = em.getCriteriaBuilder();
    }
    private Pageable getPageableuser(PageConfig Page) {
        int pagenumber = Page.getPageNumber();
        int pagesize = Page.getPageSize();
        if (Page.getPageNumber() < 0)
            pagenumber = 0;
        if (Page.getPageSize() <= 0)
            pagesize = 1;
        return PageRequest.of(pagenumber, pagesize);
    }



    public PageImpl<users> UserSearch (PageConfig orderPage, Boolean city, Integer Cityasc, int timestmpasc, String searchphrase, String type, step state) {
        try {
            CriteriaQuery<users> cq = criteriaBuilder.createQuery(users.class);
            Root<users> user = cq.from(users.class);

            cq.distinct(true);

            Predicate finalPredicate = null;
            Predicate emailpd = null;
            Predicate usernamep = null;
            Predicate cityvalue = null;
            Predicate statePredicate = null;


            if (searchphrase != null) {
                emailpd = criteriaBuilder.like(criteriaBuilder.lower(user.get("email")), "%" + searchphrase.toLowerCase() + "%");
                usernamep = criteriaBuilder.like(criteriaBuilder.lower(user.get("name")), "%" + searchphrase.toLowerCase() + "%");
                cityvalue = criteriaBuilder.like(criteriaBuilder.lower(user.get("city")), "%" + searchphrase.toLowerCase() + "%");
                finalPredicate = criteriaBuilder.or( emailpd, usernamep, cityvalue);
            }
            if (Objects.equals(type,"today")) {
                Date today = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                Date startOfToday = calendar.getTime();
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MILLISECOND, -1);
                Date endOfToday = calendar.getTime();
                Predicate todayPredicate = criteriaBuilder.between(user.get("timestmp"), startOfToday, endOfToday);
                finalPredicate = todayPredicate;
            }

            if (Objects.equals(type,"enabled")) {
                Predicate activePredicate = criteriaBuilder.equal(user.get("active"), true);

                finalPredicate = (finalPredicate == null) ? activePredicate : criteriaBuilder.and(finalPredicate, activePredicate);
            } else if (Objects.equals(type,"disabled")) {
                Predicate activePredicate = criteriaBuilder.equal(user.get("active"), false);
                finalPredicate = (finalPredicate == null) ? activePredicate : criteriaBuilder.and(finalPredicate, activePredicate);
            }

            if (state != null) {
                statePredicate = criteriaBuilder.equal(user.get("steps"), state);
                finalPredicate = (finalPredicate == null) ? statePredicate : criteriaBuilder.and(finalPredicate, statePredicate);

            }
            if (finalPredicate == null) {
                cq.select(user);
            } else {
                cq.where(finalPredicate);
            }
            if (city) {
                // If "city" is true, order by both "timestmp" and "lastname".
                if (Cityasc == 1) {
                    cq.orderBy( (timestmpasc == 1) ? criteriaBuilder.asc(user.<String>get("timestmp")) : criteriaBuilder.desc(user.<String>get("timestmp")) , criteriaBuilder.asc(user.<String>get("city")));
                } else {
                    cq.orderBy((timestmpasc == 1) ? criteriaBuilder.desc(user.<String>get("timestmp")) : criteriaBuilder.asc(user.<String>get("timestmp")) , criteriaBuilder.desc(user.<String>get("city")));
                }
            }else
            {
                // Otherwise, order only by "timestmp".
                if (timestmpasc == 1) {
                    cq.orderBy(criteriaBuilder.asc(user.<String>get("timestmp")));
                } else {
                    cq.orderBy(criteriaBuilder.desc(user.<String>get("timestmp")));
                }
            }

            TypedQuery<users> typedQuery = em.createQuery(cq);
            int usersCount = typedQuery.getResultList().size();

            typedQuery.setFirstResult(orderPage.getPageNumber() * orderPage.getPageSize());
            typedQuery.setMaxResults(orderPage.getPageSize());

            Pageable pageable = getPageableuser(orderPage);

            return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);
        } catch (NoResultException ex) {
            throw new NotFoundException("No data found.");
        }
    }

    public PageImpl<company> CompanySearch (PageConfig orderPage,Boolean city, Integer Cityasc, int timestmpasc, String searchphrase, String type) {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<company> cq = criteriaBuilder.createQuery(company.class);
            Root<company> companie = cq.from(company.class);

            cq.distinct(true);
            Predicate finalPredicate = null;


            if (searchphrase != null && !searchphrase.isEmpty()) {
                Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(companie.get("email")), "%" + searchphrase.toLowerCase() + "%");
                Predicate companyNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(companie.get("companyname").as(String.class)), "%" + searchphrase.toLowerCase() + "%");
                Predicate cityPredicate = criteriaBuilder.like(criteriaBuilder.lower(companie.get("city").as(String.class)), "%" + searchphrase.toLowerCase() + "%");
                finalPredicate = criteriaBuilder.or(emailPredicate, companyNamePredicate, cityPredicate);
            }

            if (Objects.equals("today",type)) {
                Date today = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date startOfToday = calendar.getTime();

                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MILLISECOND, -1);
                Date endOfToday = calendar.getTime();

                Predicate todayPredicate = criteriaBuilder.between(companie.get("timestmp"), startOfToday, endOfToday);
                finalPredicate = (finalPredicate == null) ? todayPredicate : criteriaBuilder.and(finalPredicate, todayPredicate);

            }

            if (finalPredicate == null) {
                cq.select(companie);
            } else {
                cq.where(finalPredicate);
            }
            if (city) {
                // If "city" is true, order by both "timestmp" and "lastname".
                if (Cityasc == 1) {
                    cq.orderBy( (timestmpasc == 1) ? criteriaBuilder.asc(companie.<String>get("timestmp")) : criteriaBuilder.desc(companie.<String>get("timestmp")) , criteriaBuilder.asc(companie.<String>get("city")));
                } else {
                    cq.orderBy((timestmpasc == 1) ? criteriaBuilder.desc(companie.<String>get("timestmp")) : criteriaBuilder.asc(companie.<String>get("timestmp")) , criteriaBuilder.desc(companie.<String>get("city")));
                }
            }else
            {
                // Otherwise, order only by "timestmp".
                if (timestmpasc == 1) {
                    cq.orderBy(criteriaBuilder.asc(companie.<String>get("timestmp")));
                } else {
                    cq.orderBy(criteriaBuilder.desc(companie.<String>get("timestmp")));
                }
            }

            TypedQuery<company> typedQuery = em.createQuery(cq);

            int usersCount = typedQuery.getResultList().size();
            typedQuery.setFirstResult(orderPage.getPageNumber() * orderPage.getPageSize());
            typedQuery.setMaxResults(orderPage.getPageSize());
            Pageable pageable = getPageableuser(orderPage);

            return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);
        } catch (NoResultException ex) {
            throw new NotFoundException(String.format("No data found."));
        }
    }



    public PageImpl<company_request> CompanyRequestSearch (PageConfig orderPage, Boolean city, Integer Cityasc, Integer timestmpasc, String searchphrase, String type, requestState state) {


        CriteriaQuery<company_request> cq = criteriaBuilder.createQuery(company_request.class);
        Root<company_request> user = cq.from(company_request.class);

        cq.distinct(true);

        Optional<Predicate> finalPredicate = null;


        if (searchphrase != null){
            Optional<Predicate> emailpd = Optional.ofNullable(searchphrase)
                    .map(phrase -> criteriaBuilder.like(criteriaBuilder.lower(user.get("phone")), "%" + phrase.toLowerCase() + "%"));
            Optional<Predicate> usernamep = Optional.ofNullable(searchphrase)
                    .map(phrase -> criteriaBuilder.like(criteriaBuilder.lower(user.get("firstname").as(String.class)), "%" + phrase.toLowerCase() + "%"));
            Optional<Predicate> cityvalue = Optional.ofNullable(searchphrase)
                    .map(phrase -> criteriaBuilder.like(criteriaBuilder.lower(user.get("message").as(String.class)), "%" + phrase.toLowerCase() + "%"));
            Optional<Predicate> lastnamevalue = Optional.ofNullable(searchphrase)
                    .map(phrase -> criteriaBuilder.like(criteriaBuilder.lower(user.get("lastname").as(String.class)), "%" + phrase.toLowerCase() + "%"));
            finalPredicate = emailpd.or(() -> usernamep).or(() -> cityvalue).or(()->lastnamevalue);

        }

        if (Objects.equals(type,"today")) {
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startOfToday = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MILLISECOND, -1);
            Date endOfToday = calendar.getTime();
            Predicate todayPredicate = criteriaBuilder.between(user.get("timestmp"), startOfToday, endOfToday);
            finalPredicate = finalPredicate.map(p -> criteriaBuilder.and(p, todayPredicate)).or(() -> Optional.of(todayPredicate));
        }
        if (state != null) {
            Predicate statePredicate = criteriaBuilder.equal(user.get("requeststate"), state);
            if (finalPredicate == null) {
                finalPredicate = Optional.of(statePredicate);
            } else
                finalPredicate = finalPredicate.map(p -> criteriaBuilder.and(p, statePredicate));

        }
        if (finalPredicate == null) {
            cq.select(user);
        } else {
            cq.where(finalPredicate.get());
        }


        if (city) {
            // If "city" is true, order by both "timestmp" and "lastname".
            if (Cityasc == 1) {
                cq.orderBy( (timestmpasc == 1) ? criteriaBuilder.asc(user.<String>get("timestmp")) : criteriaBuilder.desc(user.<String>get("timestmp")) , criteriaBuilder.asc(user.<String>get("lastname")));
            } else {
                cq.orderBy((timestmpasc == 1) ? criteriaBuilder.desc(user.<String>get("timestmp")) : criteriaBuilder.asc(user.<String>get("timestmp")) , criteriaBuilder.desc(user.<String>get("lastname")));
            }
        }else
        {
            // Otherwise, order only by "timestmp".
            if (timestmpasc == 1) {
                cq.orderBy(criteriaBuilder.asc(user.<String>get("timestmp")));
            } else {
                cq.orderBy(criteriaBuilder.desc(user.<String>get("timestmp")));
            }
        }



        TypedQuery<company_request> typedQuery = em.createQuery(cq);

        int usersCount = typedQuery.getResultList().size();
        typedQuery.setFirstResult(orderPage.getPageNumber() * orderPage.getPageSize());
        typedQuery.setMaxResults(orderPage.getPageSize());
        Pageable pageable = getPageableuser(orderPage);

        return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);

    }
    public List<Double> soldcards (Date date) {
        List<Double> dailyAmounts = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7); // go back 7 days

        for (int i = 0; i < 7; i++) {
            Date startOfDay = truncateDate(calendar.getTime());
            Date endOfDay = addDay(startOfDay);

            String jpql = "SELECT SUM(amount) FROM nfc_card WHERE timestmp >= :startOfDay AND timestmp < :endOfDay";
            TypedQuery<Double> query = em.createQuery(jpql, Double.class);
            query.setParameter("startOfDay", startOfDay);
            query.setParameter("endOfDay", endOfDay);
            Double dailyAmount = query.getSingleResult();
            dailyAmounts.add(dailyAmount != null ? dailyAmount : 0.0);

            // move calendar to the next day
            calendar.add(Calendar.DATE, 1);
        }

        return dailyAmounts;


    }
    private Date truncateDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date addDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

}

