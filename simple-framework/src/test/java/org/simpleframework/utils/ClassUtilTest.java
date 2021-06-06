package org.simpleframework.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author hike97
 * @create 2021-06-06 0:30
 * @desc 单元测试
 **/
public class ClassUtilTest {

    @DisplayName (value = "提取目标类方法")
    @Test
    public void extractPackageClass () {
        Set<Class<?>> classSet = ClassUtil.extractPackageClass ("com/simple/entity");
        System.out.println (classSet);

        Assertions.assertEquals (4, classSet.size ());

    }
}
