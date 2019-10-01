package ua.spring.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ua.spring.domain.User;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class DiscountAspect {

    public static Map<User, Integer> howManyTimesDiscountWasGivenForUser = new HashMap<>();
    public static Integer discountSum;

    @Pointcut("execution(* ua.DiscountDAOImpl.getDiscount(..))")
    private void discountWasGivenForUser() {
    }

    @Before("discountWasGivenForUser()")
    public void discountWasGivenToUser(JoinPoint joinPoint) {
        User user = (User) Arrays.stream(joinPoint.getArgs())
                .filter(x -> x.getClass().getSimpleName().equals("User"))
                .findFirst()
                .orElse(null);
        executeAspect(howManyTimesDiscountWasGivenForUser, user);
    }

    @Before("discountWasGivenForUser()")
    public static void discountWasGivenAll() {
        if (discountSum != null) {
            discountSum++;
        } else {
            discountSum = 1;
        }

    }

    private void executeAspect(Map<User, Integer> list, @Nonnull User user) {
        if (list.containsKey(user)) {
            int count = list.get(user);
            ++count;
            list.put(user, count);
        } else {
            int count = 1;
            list.put(user, count);
        }
    }

}
