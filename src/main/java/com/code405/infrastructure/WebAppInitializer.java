package com.code405.infrastructure;


import com.code405.infrastructure.filter.GZIPFilter;
import com.code405.infrastructure.root.ApplicationConfig;
import com.code405.infrastructure.servlet.MvcConfig;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.io.File;

/**
 * Created by birth on 26.01.2017.
 */
// web.xml analogue
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final String urlPattern = "/*";
    private static final String encoding = "UTF-8";
    private static final long maxUploadSizeInMb = 10 * 1024 * 1024;


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "prod");

//        //create the root Spring application context
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(ApplicationConfig.class, SecurityConfig.class);
//
//        //Create the dispatcher servlet's Spring application context
//        AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
//        servletAppContext.register(MVCConfig.class);
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
//        // throw NoHandlerFoundException to controller ExceptionHandler.class. Used for <error-page> analogue
//        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
//
//        //register and map the dispatcher servlet
//        //note Dispatcher servlet with constructor arguments
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping(urlPattern);
//
//        //add listener
//        servletContext.addListener(new ContextLoaderListener(rootContext));
//
//        //add filters
//        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter(encoding, true));
//        encodingFilter.addMappingForUrlPatterns(null, true, urlPattern);
//
//        FilterRegistration.Dynamic responseInScopeFilter = servletContext.addFilter("responseInScopeFilter", new RequestContextFilter());
//        responseInScopeFilter.addMappingForUrlPatterns(null, true, urlPattern);
//
//        FilterRegistration.Dynamic hiddenHttpMethodFilter = servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter());
//        hiddenHttpMethodFilter.addMappingForServletNames(null, true, "dispatcher");
    }

    /*
     <listener>
         <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
     </listener>
     */
    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        super.registerContextLoaderListener(servletContext);
    }


    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
        //for error-page
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, (int) (maxUploadSizeInMb / 2));
        registration.setMultipartConfig(multipartConfigElement);
    }

    //All filters
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new CharacterEncodingFilter(encoding, true),
                new RequestContextFilter(),
                new GZIPFilter(),
                new OpenEntityManagerInViewFilter()};
    }


    //Create the root Spring application context
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ApplicationConfig.class};
    }


    //Register MVC for dispatcher servlet and mapping for him
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfig.class};
    }

    //Mapping for Dispatcher servlet
    @Override
    protected String[] getServletMappings() {
        return new String[]{urlPattern};
    }
}
