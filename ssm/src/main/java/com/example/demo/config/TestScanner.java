package com.example.demo.config;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author ccq
 * @since 2021/5/6 14:20
 */
@Component
public class TestScanner implements BeanDefinitionRegistryPostProcessor {

    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {

        Set<BeanDefinitionHolder> set = new ClassPathBeanDefinitionScanner(registry) {

            @Override
            @NonNull
            protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
                return super.doScan(basePackages);
            }

            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return true;
            }

            @Override
            protected boolean isCandidateComponent(@NonNull MetadataReader metadataReader) {
                return true;
            }

        }.doScan("com.example.demo.dao");
        for (BeanDefinitionHolder holder : set) {
            AbstractBeanDefinition definition = (AbstractBeanDefinition) holder.getBeanDefinition();
            String original = definition.getBeanClassName();
            definition.setBeanClass(TestFactoryBean.class);
            ConstructorArgumentValues values = new ConstructorArgumentValues();
            values.addGenericArgumentValue(Class.forName(original));
            definition.setConstructorArgumentValues(values);
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

}
