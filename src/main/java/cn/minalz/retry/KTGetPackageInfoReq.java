package cn.minalz.retry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zcloud.health.crm.provider.constants.DateFormatConstant;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 11.1 套餐字典数据 请求实体类
 * @author zhouwei
 * @date 2023/4/3 9:10
 */
@Data
public class KTGetPackageInfoReq {

    /**
     * 院区编码
     */
    @JsonProperty(value = "org_code")
    private String orgCode;

    /**
     * 同步起始时间
     * 格式：yyyyMMddHHmmss
     */
    @JsonFormat(pattern = DateFormatConstant.YYYYMMDDHHMMSS, timezone = "GMT+8")
    @JsonProperty(value = "update_start_time")
    private LocalDateTime updateStartTime;

}
