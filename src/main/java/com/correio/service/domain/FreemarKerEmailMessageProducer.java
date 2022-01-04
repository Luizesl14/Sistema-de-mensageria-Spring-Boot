package com.correio.service.domain;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
public class FreemarKerEmailMessageProducer implements EmailMensageProducer, InitializingBean {
    private TemplateLoader templateLoader;
    private FreeMarkerConfig freemarkerConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.freemarkerConfig == null) {
            throw new FatalBeanException("Property [freemarkerConfig] of [ " + getClass().getName() + "] is required");
        }
    }

    interface TemplateLoader {
        Template load(final String name, final Locale locale, final MailFormat format) throws IOException;
    }

    static class DefaultTemplateLoader implements TemplateLoader {
        private FreeMarkerConfig config;
        private String prefix;
        private String suffix;

        public DefaultTemplateLoader(final String prefix, final String suffix, final FreeMarkerConfig config) {
            this.config = config;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        private String resolverTemplateName(final String name, final Locale locale, final MailFormat format) {
            StringBuilder result = new StringBuilder(50);
            result.append(prefix);
            result.append(locale.stripExtensions());
            result.append("_");
            result.append(locale.getCountry());
            result.append("/");
            result.append(name);
            result.append("-");
            result.append(format.toString());
            result.append(suffix);
            return result.toString();
        }

        @Override
        public Template load(String name, Locale locale, MailFormat format) throws IOException {
            Configuration configuration = config.getConfiguration();
            return configuration.getTemplate(resolverTemplateName(name, locale, format));
        }
    }

    private Template attemptLoad(final String templateName, final Locale locale, final MailFormat format) {
        try {
            return this.templateLoader.load(templateName, locale, format);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public EmailMessage produce(String emailTemplateCode, Locale locale, MailFormat format, Map<String, Object> arguments) throws TemplateException, IOException {
        EmailMessage message = new EmailMessage();
        Template template = attemptLoad(emailTemplateCode, locale, format);
        if (template == null){
            try{
                StringWriter body = new StringWriter();
                assert false;
                template.process(arguments, body);
                message.setBody(body.toString());
            }catch(TemplateException e){
                e.printStackTrace();
            }
            catch(IOException impossible){
               impossible.getCause();
            }
        }
        return message;
    }

    public void setFreeMarkerConfig(FreeMarkerConfig freemarkerConfig){
        this.freemarkerConfig = freemarkerConfig;
    }
}

