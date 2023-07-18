package cn.minalz.retry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 1.1 科室字典(门诊科室) 返回实体类
 * @author zhouwei
 * @date 2023/4/3 10:19
 */
@Data
public class KTGetDepartmentResp {

    /**
     * 科室代码
     */
    @JsonProperty(value = "subspecialty_code")
    private String subSpecialtyCode;

    /**
     * 科室名称
     */
    @JsonProperty(value = "subspecialty_desc")
    private String subSpecialtyDesc;

    /**
     * 科室名称英文
     */
    @JsonProperty(value = "subspecialty_desc_lang1")
    private String subSpecialtyDescLang1;

    /**
     * 就诊地点
     */
    private String address;

    /**
     * 科室介绍
     */
    @JsonProperty(value = "subspecialty_profile")
    private String subSpecialtyProfile;

    /**
     * 备注
     */
    private String remark;
}
