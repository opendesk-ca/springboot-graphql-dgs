package com.accounts.domain;

import com.accounts.entity.Client;
import java.util.List;

public record Account(
        Integer accountId,
        List<Client> client
) {}

