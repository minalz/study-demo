package cn.minalz.retry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zcloud.health.crm.provider.constants.DateFormatConstant;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 11.1 套餐字典数据 返回实体类
 * @author zhouwei
 * @date 2023/4/3 9:48
 */
@Data
public class KTGetPackageInfoResp {

    /**
     * 院区编码
     */
    @JsonProperty(value = "org_code")
    private String orgCode;

    /**
     * 套餐
     */
    @JsonProperty(value = "package")
    private List<PackageInfo> packageInfo;

    @Data
    public static class PackageInfo {

        /**
         * 套餐ID
         */
        @JsonProperty(value = "package_id")
        private String packageId;

        /**
         * 套餐价格
         */
        @JsonProperty(value = "package_price")
        private String packagePrice;

        /**
         * 套餐类型
         * IPT：住院套餐；OPT：门诊套餐；IOP：门诊及住院套餐；
         */
        @JsonProperty(value = "package_type")
        private String packageType;

        /**
         * 套餐最后一次更新时间
         */
        @JsonFormat(pattern = DateFormatConstant.YYYY_MM_DD_HH_MM_SS, timezone = "GMT+8")
        @JsonProperty(value = "last_updated_datetime")
        private LocalDateTime lastUpdatedDatetime;

        /**
         * 分段套餐
         */
        @JsonProperty(value = "packagepart")
        private List<PackagePart> packagePart;
    }
    @Data
    public static class PackagePart {

        /**
         * 套餐分段ID
         */
        @JsonProperty(value = "packagepart_id")
        private String packagePartId;

        /**
         * 套餐分段名称
         */
        @JsonProperty(value = "packagepart_desc")
        private String packagePartDesc;

        /**
         * 套餐分段价格
         */
        @JsonProperty(value = "packagepart_price")
        private BigDecimal packagePartPrice;

        /**
         * 套餐分段明细
         */
        @JsonProperty(value = "packagepartdetail")
        private List<PackagePartDetail> packagePartDetail;

    }

    @Data
    public static class PackagePartDetail {

        /**
         * 套餐分段明细ID
         */
        @JsonProperty(value = "packagepartdetail_id")
        private String packagePartDetailId;

        /**
         * 项目代码
         */
        @JsonProperty(value = "Item_code")
        private String itemCode;

        /**
         * 项目名称
         */
        @JsonProperty(value = "Item_desc")
        private String itemDesc;

        /**
         * 项目数量
         */
        @JsonProperty(value = "Item_qty")
        private String itemQty;

        /**
         * 项目规格
         */
        @JsonProperty(value = "Item_spec")
        private String itemSpec;

        /**
         * 项目单位
         */
        @JsonProperty(value = "Item_uom")
        private String itemUom;

        /**
         * 项目备注
         */
        @JsonProperty(value = "Item_remarks")
        private String itemRemarks;

        /**
         * 项目参考单价
         */
        @JsonProperty(value = "Item_proposed_price")
        private BigDecimal itemProposedPrice;

    }

}
