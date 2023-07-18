package cn.minalz.retry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 1.1 科室字典(门诊科室) 请求实体类
 * @author zhouwei
 * @date 2023/4/3 10:17
 */
@Data
public class KTGetDepartmentReq {

    /**
     * 院区编码
     */
    @JsonProperty(value = "org_code")
    private String orgCode;

    /**
     * 科室代码
     */
    @JsonProperty(value = "subspecialty_code")
    private String subSpecialtyCode;

    /**
     * 预约资源标识
     * Y：只显示有挂号预约资源的信息、N：显示所有科室、空值：显示所有科室
     */
    @JsonProperty(value = "appointmentresource_ind")
    private String appointmentResourceInd;

    /**
     * 渠道代码
     * WEBAPP:网站系统平台、SELFMACHINE:自助机、WXAPP：微信平台、HAIYIN：海银CRM
     */
    @JsonProperty(value = "channelflag")
    private String channelFlag;
}
