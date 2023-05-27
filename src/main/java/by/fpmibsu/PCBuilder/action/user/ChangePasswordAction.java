package by.fpmibsu.PCBuilder.action.user;

import by.fpmibsu.PCBuilder.action.ActionError;
import by.fpmibsu.PCBuilder.dao.DaoException;
import by.fpmibsu.PCBuilder.entity.User;
import by.fpmibsu.PCBuilder.entity.component.utils.Authentication;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePasswordAction extends UserAction{
    public ChangePasswordAction(@NotNull HttpServletRequest req, @NotNull HttpServletResponse res) throws DaoException {
        super(req, res);
    }
    @Override
    public void doAction() {
        String login = reqData.get("login").getAsString();
        String newPassword = reqData.get("newPassword").getAsString();
        boolean fromGoogleAcc = reqData.get("googleAccount").getAsBoolean();
        if(fromGoogleAcc) {
            return;
        }
        try {
            if(!service.isExist(login)) {
                ActionError.sendError(res, "no user with this login");
                return;
            }
            User user = service.getUser(login);
            if(user.isFromGoogle()) {
                ActionError.sendError(res, "google account");
                return;
            }
            if(Authentication.isCorrectPassword(newPassword, user.getHashPassword())) {
                ActionError.sendError(res, "old password");
                return;
            }
            if(newPassword.length() < 6) {
                ActionError.sendError(res, "password should be at least 6 characters");
                return;
            }
            service.changePassword(user,newPassword);
        } catch (Exception e) {
            ActionError.sendError(res);
        }
    }
}
