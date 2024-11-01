package com.ayoub.project_management.service.subscription;

import com.ayoub.project_management.model.PlanType;
import com.ayoub.project_management.model.Subscription;
import com.ayoub.project_management.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUsersSubscription(Long userId)throws Exception;
    Subscription upgradSubscription(Long userId, PlanType planType)throws Exception;
    boolean isValid(Subscription subscription);
}
