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
import sun.net.httpserver.AuthFilter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.relation.Role;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ManagedBean
@SessionScoped
public class userManagedBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    @Inject
    private UserService userService = new UserService();

    @Inject
    private KweetService kweetService = new KweetService();

    private User loginUser;
    private User selectedUser;
    private Kweet selectedKweet;
    private User currentUser;
    @Size(min=3, max=26)
    private String username;
    @Size(min=3, max=26)
    private String password;
    @Size(min=3, max=26)
    private String usernameRegister;
    @Size(min=3, max=26)
    private String nameRegister;
    @Size(min=3, max=26)
    private String passwordRegister;
    @Size(min=3, max=26)
    private String passwordRepeatRegister;
    @Size(min=3, max=26)
    private String searchUser;
    private List<User> searchUsersResults;
    private List<User> allUsers;
    private List<Kweet> allUserKweets;
    private List<String> roles;
    private String selectedRole;

    @PostConstruct
    public void init(){
        allUsers = userService.getAllUsers();
        roles = new ArrayList<>();
        roles.add("ADMINISTRATOR");
        roles.add("MODERATOR");
        roles.add("STANDARD");
        selectedRole = roles.get(0);
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public String removeKweet(){
        if(selectedKweet != null){
            kweetService.removeKweet(selectedKweet.getId(), selectedUser.getId());
            allUserKweets = kweetService.getAllKweets(selectedUser.getId());
        }
        return "succes";
    }

    public String validateUsernamePassword() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(username.isEmpty() || password.isEmpty()){
            facesContext.addMessage("loginForm:usernameLoginError", new FacesMessage("Please enter a valid username and Password."));
            return "login";
        }

        User findUser = userService.findSingleUser(username);
        boolean valid = false;
        if(findUser != null){
            valid = true;
        }

        if(findUser.getRole().equals(Roles.STANDARD)){
            facesContext.addMessage("loginForm:generalLoginError", new FacesMessage("Incorrect user role. Only for administrators and moderators."));
            return "login";
        }
        else if (valid) {
            User user = userService.validateUser(username, password);
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userid", user.getId());
            session.setAttribute("user", user);
            redirectProfile();
            return "profile";
        } else {
            facesContext.addMessage("loginForm:usernameLoginError", new FacesMessage("Please enter a valid username and Password."));
            return "login";
        }
    }

    public String getSessionUsername(){
        HttpSession session = SessionUtils.getSession();
        return (String)session.getAttribute("username");
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        redirectDashboard();
        return "login";
    }

    public void redirectDashboard(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void redirectProfile(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            facesContext.getExternalContext().redirect("profile.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateAdmin(){
        updateProfile(roles.get(0));
    }

    public void updateMod(){
        updateProfile(roles.get(1));
    }

    public void updateStandard(){
        updateProfile(roles.get(2));
    }

    private void updateProfile(String value){
        if(selectedUser != null){
            selectedUser.setRole(Roles.valueOf(value));
            userService.updateUser(selectedUser, selectedUser.getId());
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
