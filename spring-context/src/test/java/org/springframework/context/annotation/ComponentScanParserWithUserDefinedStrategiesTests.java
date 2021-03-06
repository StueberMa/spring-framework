/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

import org.junit.Test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mark Fisher
 */
public class ComponentScanParserWithUserDefinedStrategiesTests {

	@Test
	public void testCustomBeanNameGenerator() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"org/springframework/context/annotation/customNameGeneratorTests.xml");
		assertTrue(context.containsBean("testing.fooServiceImpl"));
	}

	@Test
	public void testCustomScopeMetadataResolver() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"org/springframework/context/annotation/customScopeResolverTests.xml");
		BeanDefinition bd = context.getBeanFactory().getBeanDefinition("fooServiceImpl");
		assertEquals("myCustomScope", bd.getScope());
		assertFalse(bd.isSingleton());
	}

	@Test
	public void testInvalidConstructorBeanNameGenerator() {
		assertThatExceptionOfType(BeansException.class).isThrownBy(() ->
			new ClassPathXmlApplicationContext(
					"org/springframework/context/annotation/invalidConstructorNameGeneratorTests.xml"));
	}

	@Test
	public void testInvalidClassNameScopeMetadataResolver() {
		assertThatExceptionOfType(BeansException.class).isThrownBy(() ->
				new ClassPathXmlApplicationContext(
						"org/springframework/context/annotation/invalidClassNameScopeResolverTests.xml"));
	}

}
