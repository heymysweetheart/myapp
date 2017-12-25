## filter
We define filters from time to time to do some work in one place. Here is the way to add a filter to spring boot 
application.
### java Configuration
Create a `Configuration` bean extends `org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter`. 
Then specific configurations could be added in this bean.
    @Configuration
    public class WebConfigurator extends WebMvcConfigurerAdapter {
    
      @Bean
      public FilterRegistrationBean getParamFilter() {
        ParameterFilter parameterFilter = new ParameterFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(parameterFilter);
    
        List<String> urlPatterns= new ArrayList<>();
        urlPatterns.add("/*"); //拦截路径，可以添加多个
        urlPatterns.add("/login"); //拦截路径，可以添加多个
    
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        filterRegistrationBean.setOrder(1);
    
        return filterRegistrationBean;
      }
    
    }
    
### Using annotations
Use `WebFilter` to configure the filter.
   ```
   @Component
   @Order(Ordered.HIGHEST_PRECEDENCE)
   @WebFilter(filterName = "requestFilter", urlPatterns = "/*")
   public class RequestFilter implements Filter {
     @Override
     public void init(FilterConfig filterConfig) throws ServletException {
   
     }
   
     @Override
     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       try {
         String mdcData = String.format("requestId:%s", UUID.randomUUID().toString().replace("-", "").substring(0, 12));
         MDC.put("mdcData", mdcData);
         filterChain.doFilter(servletRequest, servletResponse);
       } finally {
         MDC.clear();
       }
     }
   
     @Override
     public void destroy() {
   
     }
   }
