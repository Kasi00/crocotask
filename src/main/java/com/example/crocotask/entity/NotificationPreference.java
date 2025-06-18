package com.example.crocotask.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class NotificationPreference {
    private boolean emailEnabled;
    private boolean smsEnabled;
    private boolean promotionalEnabled;
}
