package com.pfg.interceptors;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Component
public class CalendarInterceptor implements HandlerInterceptor {

    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        StringBuilder message = new StringBuilder("");
        if (hour >= open && hour < close) {
            message.append("Bienvenidos al horario de atención a clientes, atendemos desde las ");
            message.append(open);
            message.append(":00 hrs.");
            message.append(" hasta las ");
            message.append(close);
            message.append(":00 hrs.");
            message.append(" ¡Gracias por su visita!");
            request.setAttribute("message", message.toString());
        } else {
            message.append("Cerrado, fuera del horario de atención por favor visítenos desde las ");
            message.append(open);
            message.append(":00 y las ");
            message.append(close);
            message.append(":00 hrs. ¡Gracias!");
            request.setAttribute("message", message.toString());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
