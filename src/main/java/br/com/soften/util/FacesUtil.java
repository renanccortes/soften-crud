package br.com.soften.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;

/**
 * Contém várias funções relacionadas ao JSF, ne todos uso no momento
 *
 * @author Renan
 */
public class FacesUtil {

    public static final Integer ERRO = 0;
    public static final Integer AVISO = 1;
    public static final Integer ERRO_FATAL = 2;
    public static final Integer INFORMACAO = 3;

    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static String gerarHash(String frase, String algoritmo) {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            bytes = md.digest();
        } catch (NoSuchAlgorithmException e) {

        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }

    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static String getUrlAttribute(String name) {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParameter(name);
    }

    public static void cleanSubmittedValues(UIComponent component) {
        if (component instanceof EditableValueHolder) {
            EditableValueHolder evh = (EditableValueHolder) component;
            evh.setSubmittedValue(null);
            evh.setValue(null);
            evh.setLocalValueSet(false);
            evh.setValid(true);
        }
        if (component.getChildCount() > 0) {
            for (UIComponent child : component.getChildren()) {
                cleanSubmittedValues(child);
            }
        }
    }

//metodo que redireciona as paginas
    public static void redirecionar(String url) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext contextEx = facesContext.getExternalContext();
            contextEx.redirect(url);
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao redirecionar para a pagina " + url + ", " + e, ""));
        }

    }

    public static SelectItem[] createFilterOptions(String[] data) {
        SelectItem[] options;
        if (data != null) {
            options = new SelectItem[data.length + 1];
            options[0] = new SelectItem("", "Selecione");
            for (int i = 0; i < data.length; i++) {
                options[i + 1] = new SelectItem(data[i], data[i]);
            }
        } else {
            options = new SelectItem[1];
            options[0] = new SelectItem("", "Selecione");
        }

        return options;
    }

    public static String getCaminhoAbsoluto(String pasta) {

        return FacesContext.getCurrentInstance().getExternalContext().getRealPath(pasta);
    }

    public static ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

    public static String getContextParam(String contextParam) {
        return getServletContext().getInitParameter(contextParam);
    }

    public static ExternalContext getExternalContext() {
        FacesContext fc = FacesContext.getCurrentInstance();

        return fc.getExternalContext();
    }

    public static HttpSession getHttpSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(create);
    }

    /**
     * Get managed bean based on the bean name.
     *
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
    public static Object getManagedBean(String beanName) {

        return getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
    }

    /**
     * Remove the managed bean based on the bean name.
     *
     * @param beanName the bean name of the managed bean to be removed
     */
    public static void resetManagedBean(String beanName) {
        getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
    }

    /**
     * Store the managed bean inside the session scope.
     *
     * @param beanName the name of the managed bean to be stored
     * @param managedBean the managed bean to be stored
     */
    public static void setManagedBeanInSession(String beanName, Object managedBean) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
    }

    /**
     * Get parameter value from request scope.
     *
     * @param name the name of the parameter
     * @return the parameter value
     */
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    /**
     * Add information message.
     *
     * @param msg the information message
     */
    public static void addInfoMessage(String msg) {
        addInfoMessage(null, msg);
    }

    /**
     * Add information message to a specific client.
     *
     * @param clientId the client id
     * @param msg the information message
     */
    public static void addInfoMessage(String clientId, String msg) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }

    /**
     * Add error message.
     *
     * @param msg the error message
     */
    public static void addErrorMessage(String msg) {
        addErrorMessage(null, msg);
    }

    /**
     * Add error message to a specific client.
     *
     * @param clientId the client id
     * @param msg the error message
     */
    public static void addErrorMessage(String clientId, String msg) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    private static Application getApplication() {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }

    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }

    private static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    private static Object getElValue(String el) {
        return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
    }

    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }

    public static void mostraDialog(String dialog, boolean mostrar) {
        try {

            dialog = "PF('" + dialog + "').";
            PrimeFaces current = PrimeFaces.current();
            if (mostrar) {

                current.executeScript(dialog + "show();");

            } else {
                current.executeScript(dialog + "hide();");
            }
        } catch (Exception e) {

        }
    }

    public static byte[] uploadedFileToByte(UploadedFile file) {

        try {
            InputStream is = file.getInputstream();
            byte[] bytes = IOUtils.toByteArray(is);
            return bytes;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static void atualiza(String... ids) {
        for (String id : ids) {
            PrimeFaces.current().ajax().update(id);
        }
    }

    public static String getUrl() {
        HttpServletRequest request = getRequest();
        return request.getRequestURI();
    }

    public static String getUrlCompleta() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        return request.getRequestURL().toString();
    }

    public static String getIpUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        return request.getRemoteAddr();
    }

    public static String getNavegador() {

        HttpServletRequest request = getRequest();

        Enumeration headers = request.getHeaderNames();
        String browser = "";
        while (headers.hasMoreElements()) {
            String header = ((String) headers.nextElement()).toLowerCase();
            if (header.equals("user-agent")) {
                browser = request.getHeader(header);
                return browser;
            }
        }

        return browser;
    }

    private static HttpServletRequest getRequest() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return (HttpServletRequest) fc.getExternalContext().getRequest();
    }

    /* Resolve o problemas das managed bean em viewscoped */
    public static void atualizarPagina() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
    }

}
