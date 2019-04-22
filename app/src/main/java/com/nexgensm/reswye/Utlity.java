package com.nexgensm.reswye;

import com.nexgensm.reswye.api.ApiClient;

public class Utlity {
    public static final String leadID = "lead_Id";
    public static final String savePropertyPhotoUrl="http://202.88.239.14:8169/api/Lead/savepropertyPhotos";
    public static  final String testToken ="bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1haGVzaGNtODBAZ21haWwuY29tIiwianRpIjoiM2NlZDk2OTYtMzNlNy00ZGUyLWIzZjQtZDNiZjhkYmY1MGM2IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiQWdlbnQiLCJuYmYiOjE1MjUwOTMyMjIsImV4cCI6MTUzMDI3NzIyMiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo2MzkzOS8iLCJhdWQiOiIxMjM0NTYifQ.P6cP64r7WWoEPA_PxCZHOD1iIQZybwWrI7Xu_s3leD8";
    public static final String imageFormat="Base64Image";
    public static final String getPropertyPhotoUrl="http://202.88.239.14:8169/api/Lead/GetPropertyImages/leadid";
    public static final String imageUrl ="http://202.88.239.14:8169/FileUploads/";
    public static final String savesortingCriteriaUrl ="http://202.88.239.14:8169/api/Lead/saveSortingCriteria";
    public static final String sortingUrl="http://202.88.239.14:8169/api/Lead/GetleadsortedList";
    public static  final String followUpCountUrl ="http://202.88.239.14:8169/api/Lead/followupCounts/userId";
    public static final String testImageBase64 ="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";
    public static final String leadFilterUrl ="http://202.88.239.14:8169/api/Lead/GetLeadfilteringresult";
//
//public static final String SERVER_URL = "http://www.ededge.in/allspice/AndroidUploadImage/UploadToServer.php";
    public static final String SERVER_URL = "http://202.88.239.14:8169/api/lead/propertyDocUpload";

    public static final String AppointmentListUrl =ApiClient.BASE_URL+"appoinmentslistcalender";

}
