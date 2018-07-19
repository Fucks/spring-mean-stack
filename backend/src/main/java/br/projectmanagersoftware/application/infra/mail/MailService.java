package br.projectmanagersoftware.application.infra.mail;

import com.adobe.xmp.impl.Base64;
import freemarker.template.Configuration;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 05/10/2016
 */
@Component
@PropertySource("classpath:mail.properties")
public class MailService{

    private Logger LOG = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Value("${mail.local.host}")
    private String mailHost;

    /**
     * Serviço para envio de um e-mail.
     *
     * @param body
     * @param titulo
     */
    @Async
    public void mailSendBroadcast(String titulo, String body) throws InterruptedException {
        LOG.info("-->Iniciando broadcast de e-mails");

        LOG.info("-->Finalizado broadcast de e-mails");
    }

    /**
     * Notificação de alteração de status nos cadastros/ documentos de
     * fornecedores.
     *
     * @param model
     */
    @Async
    public void notificationSend(Map<String, Object> model) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setFrom("fucksinit@public.init");
                message.setTo(String.valueOf(model.get("email")));
                message.setSubject("Notificação");

                String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("notificacao-template.html"), model);

                message.setText(text, true);
            }
        };

        this.mailSender.send(preparator);
    }

    /**
     * Envia e-mail para resetar a senha do usuário.
     *
     * @param model
     */
    @Async
    public void resetPasswordMailSend(Map<String, Object> model) {
        LOG.info("-->Enviando e-mail para resetar senha");
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setFrom("fucksinit@public.init");
                message.setTo(String.valueOf(model.get("email")));
                message.setSubject("Solicitação de alteração de senha");

                model.put("mailhost", mailHost);

                String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("notificacao-esquecimento-senha-template.html"), model);

                message.setText(text, true);
            }
        };

        this.mailSender.send(preparator);
        LOG.info("-->E-mail enviado");
    }

    /**
     * Envia e-mail com link de confirmação de cadastro.
     *
     * @param model
     */
    @Async
    public void mailConfirmationSend(String email, String userNome, Long userId) {
        LOG.info("-->Enviando e-mail para ativação do cadastro");
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                final Map<String, Object> model = new HashMap<>();
                
                model.put("nome", userNome);
                model.put("token", Base64.encode(userId.toString()));
                model.put("mailhost", mailHost);
                
                message.setFrom("fucksinit@public.init");
                message.setTo(email);
                message.setSubject("Registro no portal de fornecedores");

                String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("notificacao-ativacao-cadastro.html"), model);

                message.setText(text, true);
            }
        };

        this.mailSender.send(preparator);
        LOG.info("-->E-mail enviado");
    }
}
