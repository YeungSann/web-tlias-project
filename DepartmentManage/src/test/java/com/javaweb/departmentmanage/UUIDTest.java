package com.javaweb.departmentmanage;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {
    // 测试uuid
    @Test
    public void testUUID() {
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }
    }
}
