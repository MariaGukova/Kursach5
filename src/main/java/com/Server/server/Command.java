package com.Server.server;

import java.io.Serializable;

public enum Command implements Serializable {
    CREATE,
    READ,
    UPDATE,
    DELETE,

    EXIT
}

