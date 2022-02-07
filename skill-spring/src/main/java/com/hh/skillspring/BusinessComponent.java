package com.hh.skillspring;

import org.springframework.stereotype.Component;

/**
 * @author HaoHao
 * @date 2021/9/27 2:36 下午
 */
@Component
public class BusinessComponent {

    @LogAspect.LogAspectAnnotation
    public void doBiz() {

    }

}
