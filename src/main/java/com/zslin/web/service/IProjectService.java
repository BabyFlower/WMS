package com.zslin.web.service;

import com.sun.javaws.progress.PreloaderDelegate;
import com.zslin.web.model.Project;
import org.codehaus.groovy.antlr.java.PreJava2GroovyConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/9/25.
 */

@Service
public interface IProjectService extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
    @Query("FROM Project d where d.id=:id")
    Project findById(@Param("id")Integer id);

}