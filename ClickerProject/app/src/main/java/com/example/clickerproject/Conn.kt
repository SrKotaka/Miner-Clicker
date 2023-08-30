package com.example.clickerproject

import java.sql.Connection
import java.sql.DriverManager

object Conn {
    private const val DB_URL = "jdbc:mysql://aws.connect.psdb.cloud/bd22144?sslMode=VERIFY_IDENTITY"
    private const val DB_USER = "0ojn8rd1a1lyk21toa0v"
    private const val DB_PASS = "pscale_pw_L4RMj0W7TCdMg3mCpEAMmlEc8KrSrmBb7K3zhJtlzZ6"

    fun getConnection(): Connection {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)
    }
}
