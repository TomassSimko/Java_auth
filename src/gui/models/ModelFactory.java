//package gui.models;
//
//import be.User;
//import bll.exceptions.UserDAOException;
//import bll.exceptions.UserServiceException;
//
//public class ModelFactory {
//
//
//    public enum ModelType{
//        USER_MODEL,
//        ROLE_MODEL,
//    }
//
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
//
//    // add more testing classes into this one with different test methods
//}
