package cn.minalz.retry;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;

@AllArgsConstructor
@Service
public class RetryServiceImpl {

    private static final int MAX_RETRIES = 5; // 最大重试次数
    private final HisRequestServiceImpl hisRequestService;
//    private final CallBackServiceImpl callBackServiceImpl;

    public void retry() {


        String apiPath = "http://10.12.0.125:9090/ktin/api/flow/KTGetPackageInfo";
//        KTGetDepartmentReq param = new KTGetDepartmentReq();
//        param.setChannelFlag("HAIYIN");
//        param.setAppointmentResourceInd("Y");
//        param.setOrgCode("JK0027");
        KTGetPackageInfoReq param = new KTGetPackageInfoReq();

        callRemoteService(apiPath, param, KTGetPackageInfoResp.class);

        // 使用匿名类作为回调方法的实现
        this.add(apiPath, param, KTGetPackageInfoResp.class, new CallbackService() {
            @Override
            public <T1, T2> T2 onComplete(String apiPath, T1 t1, Class<T2> beanType) {
                return hisRequestService.postForObject(apiPath, t1, beanType);

//                return callRemoteService(apiPath, t1, beanType);
            }

        });

        // 调用示例
    }

    public <T1, T2> T2 add(String apiPath, T1 req, Class<T2> beanType, CallbackService callbackService) {
        return callbackService.onComplete(apiPath, req, beanType);
    }

    private <T1, T2> T2 callRemoteService(String apiPath, T1 req, Class<T2> beanType, CallbackService callbackService) {
        try {
            T2 t2 = callbackService.onComplete(apiPath, req, beanType);
            // 判断是否成功

            if (callSuccessful) {
                System.out.println("服务调用成功！");
            } else {
                System.out.println("服务调用失败，进行重试和补偿操作...");
                retryAndCompensate();
            }
        } catch (Exception e) {
            System.out.println("服务调用失败，进行重试和补偿操作...");
            retryAndCompensate();
        }
    }

    private boolean makeRemoteServiceCall() {
        // 模拟服务调用失败的情况
        double randomValue = Math.random();
        return randomValue >= 0.5;
    }

    private void retryAndCompensate() {
        try {
            // 将任务信息插入数据库
            String taskData = "Task Data"; // 这里应该是实际任务数据
            insertRetryTask(taskData);

            // 从数据库中获取任务并进行重试
            retryTasks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRetryTask(String taskData) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_name"; // 替换为实际的数据库连接URL和数据库名
        String user = "your_username"; // 替换为实际的数据库用户名
        String password = "your_password"; // 替换为实际的数据库密码

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO retry_task (task_data, status) VALUES (?, 'NEW')";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, taskData);
                stmt.executeUpdate();
            }
        }
    }

    private void retryTasks() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_name"; // 替换为实际的数据库连接URL和数据库名
        String user = "your_username"; // 替换为实际的数据库用户名
        String password = "your_password"; // 替换为实际的数据库密码

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String selectSQL = "SELECT * FROM retry_task WHERE status = 'NEW' AND retry_count < ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
                selectStmt.setInt(1, MAX_RETRIES);

                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        int taskId = resultSet.getInt("id");
                        String taskData = resultSet.getString("task_data");

                        // 模拟任务重试
                        boolean callSuccessful = makeRemoteServiceCall();

                        // 更新任务状态和重试次数
                        String updateSQL = "UPDATE retry_task SET status = ?, retry_count = retry_count + 1 WHERE id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                            if (callSuccessful) {
                                updateStmt.setString(1, "SUCCESS");
                            } else {
                                updateStmt.setString(1, "FAILED");
                            }
                            updateStmt.setInt(2, taskId);
                            updateStmt.executeUpdate();
                        }
                    }
                }
            }
        }
    }
}
