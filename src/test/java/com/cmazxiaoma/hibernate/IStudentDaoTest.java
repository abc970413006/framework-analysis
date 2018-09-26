package com.cmazxiaoma.hibernate;

import com.cmazxiaoma.InitSpringTest;
import com.cmazxiaoma.model.School;
import com.cmazxiaoma.model.Student;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/7/3 18:00
 */
public class IStudentDaoTest extends InitSpringTest {

    @Resource
    private IStudentDao studentDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void query() {
        Student student = studentDao.findOne("1");
        System.out.println("student=" + student);
        System.out.println("student.id=" + student.getId());

        School school = student.getSchool();
        System.out.println("school=" + school);

        System.out.println("=======================");

        Student student1 = studentDao.findOne("1");

        System.out.println("student=" + student1);
        System.out.println("student.id=" + student1);

        School school1 = student1.getSchool();
        System.out.println("school1=" + school1);
    }

    /**
     * 1.org.hibernate.MappingException:
     * Repeated column in mapping for entity: com.cmazxiaoma.model.Student column:
     * school_id (should be mapped with insert="false" update="false")
     *
     * 2.制造org.hibernate.ObjectNotFoundException: No row with the given indentifier exists
     */
    @Test
    public void throwException() {
        // 如果用@NotFound注解，会让懒加载机制失效
        Student student = studentDao.findOne("1");
        System.out.println("student=" + student);
        System.out.println("school=" + student.getSchool());
    }

    /**
     * JPA的一级缓存
     */
    @Test
    public void oneLevel() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Student student = entityManager.find(Student.class, "1");

            System.out.println("student=" + student);
            System.out.println("school=" + student.getSchool());
            System.out.println("=====查看缓存========");


            Student student1 = entityManager.find(Student.class, "1");
            System.out.println("student1=" + student1);
            System.out.println("school1=" + student.getSchool());

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}