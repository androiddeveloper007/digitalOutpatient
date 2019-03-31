// IVimsAidlInterface.aidl
package com.bainuosoft.aidlInterface;

// Declare any non-default types here with import statements

interface IVimsAidlInterface {

    /**
    * 根据疫苗型号，厂家，批次号，打开冰箱小抽屉锁
    * 返回值json ：操作成功  返回空字符串 {"IsSuccess":"true","OutMessage":""}
    *                                 {"IsSuccess":"true","OutMessage":"疫苗没有在当前冰箱中"}
    *          操作失败  返回失败原因 {"IsSuccess":"false",OutMessage:"疫苗型号[流脑A]不存在"}
    */
    String openDoorByVaccinerInfo(in String vaccineModelNo,in String supperlyNm,in String batchNo);

    /**
    * 扫描电子监管码  检验合法性
    * isShare（是否共用）：含义为一支多人份疫苗，接种时如果是一支多人份的疫苗，接种完成后，给用户提示：[是否放回原来的抽屉？ ]     用户选择【是】 表示本支疫苗还未用完，需要自动弹出抽屉；如果选择【否】 则需要扣减库存；
    * 这个接口 判断 isShare=true的疫苗在接种完成后 需要提醒用户【是否放回原来的抽屉】。
    * 返回值json： 成功 {"IsSuccess":"true","OutMessage":"",isShare:"true"}
    *          失败 {"IsSuccess":"false","OutMessage":isShare:"false"}
    */
    String scanVaccineBarCode(in String vaccineModelNo,in String supperlyNm,in String batchNo,in String barcode);
 /**
       *   接种完成 扣减疫苗库存
       *   barcode: 电子监管码
       *   isDeduction（0 不需要扣减，1 需要扣减）:是否需要扣减库存   （一支多人份的，用户选择 需要放回的，不需要扣减库存）
       *   degree 剂次
       *   vaccineInoculatePartNm 接种部位   左上臂,右上臂，口服,其他，左大腿, 右大腿
       *   vaccineInoculateTypeNm 接种途径  肌内,皮下，皮内,口服，其它
       *   isFree 是否免费 0 否 1 是
       *   mark 备注
       *   VaccineInoculator(接种人信息 json)
       *    	 {
       *    		"no": "001",
       *    		"name": "张三",
       *    		"idCard": "xxxxx",
       *    		"birthdate": "2012-06-06",
       *    		"gender": "女",
       *    		"address": "xxxxxx",
       *    		"fatherName": "xxxx",
       *    		"motherName": "xxxxx"
       *    	  }
       *   返回值json ： 成功 {"IsSuccess": "true","OutMessage": ""}
       *             失败 {"IsSuccess": "false","OutMessage": "失败原因"}
       */
       String vaccineConfirm(in String barcode,in int isDeduction,in int degree,in String vaccineInoculatePartNm,in String vaccineInoculateTypeNm,in int isFree,in String mark,in String vaccineInoculatorInfo);
}
