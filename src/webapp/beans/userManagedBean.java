package beans;


import entities.Kweet;
import entities.Roles;
import entities.User;
import javafx.scene.control.Alert;
import org.openfaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import services.KweetService;
import services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.relation.Role;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ManagedBean
@SessionScoped
public class userManagedBean {

    @Inject
    private UserService userService = new UserService();

    @Inject
    private KweetService kweetService = new KweetService();

    private User selectedUser;
    private Kweet selectedKweet;
    private User currentUser;
    private String username;
    private String password;
    private String usernameRegister;
    private String nameRegister;
    private String passwordRegister;
    private String passwordRepeatRegister;
    private String searchUser;
    private List<User> searchUsersResults;
    private List<User> allUsers;
    private List<Kweet> allUserKweets;
    private String selectedRole;

    @PostConstruct
    public void init(){
        allUsers = userService.getAllUsers();
    }

    public Kweet getSelectedKweet() {
        return selectedKweet;
    }

    public void setSelectedKweet(Kweet selectedKweet) {
        this.selectedKweet = selectedKweet;
    }

    public List<Kweet> getAllUserKweets() {
        return allUserKweets;
    }

    public void setAllUserKweets(List<Kweet> allUserKweets) {
        this.allUserKweets = allUserKweets;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public String getUsernameRegister() {
        return usernameRegister;
    }

    public void setUsernameRegister(String usernameRegister) {
        this.usernameRegister = usernameRegister;
    }

    public String getNameRegister() {
        return nameRegister;
    }

    public void setNameRegister(String nameRegister) {
        this.nameRegister = nameRegister;
    }

    public String getPasswordRegister() {
        return passwordRegister;
    }

    public void setPasswordRegister(String passwordRegister) {
        this.passwordRegister = passwordRegister;
    }

    public String getPasswordRepeatRegister() {
        return passwordRepeatRegister;
    }

    public void setPasswordRepeatRegister(String passwordRepeatRegister) {
        this.passwordRepeatRegister = passwordRepeatRegister;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getSearchUsersResults() {
        return searchUsersResults;
    }

    public void setSearchUsersResults(List<User> searchUsersResults) {
        this.searchUsersResults = searchUsersResults;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public String removeKweet(){
        if(selectedKweet != null){
            kweetService.removeKweet(selectedKweet.getId(), selectedUser.getId());
            allUserKweets = kweetService.getAllKweets(selectedUser.getId());
        }
        return "succes";
    }

    public void login(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean loggedIn = false;

        if(username.isEmpty() || password.isEmpty()){
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "login error", "No username and/or password");
        }
        if(username != null && password != null){
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "welcome", username);

            try {
                facesContext.getExternalContext().redirect("profile/profile.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            loggedIn =false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "login error", "invalid credentials");
        }


        //requestContext.addCallbackParam("loggedIn", loggedIn);
    }

    public void redirectDashboard(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("main/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectProfile(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("profile/profile.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String outcome(){
        allUserKweets = kweetService.getAllKweets(selectedUser.getId());
        return  "result";
    }

    public void updateSelectedUser(User user){
        selectedUser = user;
        if(selectedUser != null){
            //allUserKweets = selectedUser.getKweets();
            allUserKweets = kweetService.getAllKweets(selectedUser.getId());
        }
    }

    public void register(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        boolean valid = true;


        if(userService.createUser(new User(usernameRegister, nameRegister, passwordRegister, "", "", selectedRole))){
            redirectProfile();
        }

        /*if(usernameRegister.isEmpty() || nameRegister.isEmpty() || passwordRegister.isEmpty() || passwordRepeatRegister.isEmpty() || selectedRole.isEmpty()){
            message = new FacesMessage("Fill in all the fields of the register form");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(usernameRegister, message);
            facesContext.renderResponse();
            valid = false;
        }
        else if(passwordRepeatRegister != passwordRegister){
            message = new FacesMessage("Password and repeat password must be the same");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(passwordRegister, message);
            facesContext.renderResponse();
            valid = false;
        }
        else if(userService.findUserByUsername(usernameRegister) != null){
            message = new FacesMessage("User with this username already exists");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(usernameRegister, message);
            facesContext.renderResponse();
            valid = false;
        }

        if(valid){
            userService.createUser(new User(usernameRegister, nameRegister, passwordRegister, "", "", selectedRole));
        }*/
    }

    public List<Kweet> getAllKweets(){
        if(selectedUser == null){
            return null;
        }
        return kweetService.getAllKweets(selectedUser.getId());
    }

    public void searchStringValueChanged()
    {
        System.out.println(searchUser);
        this.searchUser = searchUser;
    }

    public List<User> searchAllUser(){
        String username = (this.searchUser == null)? "": this.searchUser.trim();
        this.searchUsersResults = userService.findUserByUsername(username);
        return searchUsersResults;
    }

    public List<User> collectUsers(){
        return this.userService.getAllUsers();
    }

    public void onUserSelect(SelectEvent event){

    }

    public void onUserUnselect(SelectEvent event){

    }
}
