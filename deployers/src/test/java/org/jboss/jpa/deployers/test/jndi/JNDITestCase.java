/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.jpa.deployers.test.jndi;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;

import org.jboss.jpa.deployers.test.common.PersistenceTestCase;
import org.jboss.jpa.deployers.test.common.Person;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class JNDITestCase extends PersistenceTestCase
{
   @BeforeClass
   public static void beforeClass() throws Exception
   {
      PersistenceTestCase.beforeClass();
      
      deploy(JNDITestCase.class.getResource("/org/jboss/jpa/deployers/test/jndi"));
   }
   
   @Test
   public void testEM() throws Exception
   {
      InitialContext ctx = new InitialContext();
      TransactionManager tm = (TransactionManager) ctx.lookup("java:/TransactionManager");
      EntityManager em = (EntityManager) ctx.lookup("JndiEM");
      tm.begin();
      try
      {
         Person p = new Person();
         p.setName("Debby");
         em.persist(p);
      }
      finally
      {
         tm.rollback();
      }
   }
   
   @Test
   public void testEMF() throws Exception
   {
      InitialContext ctx = new InitialContext();
      EntityManagerFactory emf = (EntityManagerFactory) ctx.lookup("JndiEMF");
      EntityManager em = emf.createEntityManager();
      Person p = new Person();
      p.setName("Debby");
      em.persist(p);
   }
}
