package gui.models;


import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;

public class ModelFactory {

    public enum ModelType{
        USER_MODEL,
        ROLE_MODEL,
    }

    public static IUserModel createUserModel()  {
        try {
            return new UserModel();
        } catch (UserManagerException | UserDAOException e) {
            throw new RuntimeException(e);
        }
    }
//    private static IUserModel userModel;
//
//    public ModelFactory(IUserModel userModel) throws UserServiceException, UserDAOException {
//        this.userModel = new UserModel(userModel);
//    }
//
//    // should return Interface
//
//    public static IUserModel createModel(ModelType modelType) throws UserServiceException, UserDAOException {
//        if (modelType == ModelType.USER_MODEL) {
//            new UserModel(userModel);
//        }
//        throw new UserServiceException("probliem happened ",new Exception());
//    }

    // add more testing classes into this one with different test methods
}
