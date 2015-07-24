package com.daoshun.guangda.serviceImpl;


import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.service.ICoinRecordService;
import com.daoshun.guangda.service.ICouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tutu on 15/7/24.
 */

@Service("CoinService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CoinRecordServiceImpl extends BaseServiceImpl implements ICoinRecordService {


    @Override
    public void addCoinRecord(CoinRecordInfo coinRecordInfo) {
            dataDao.addObject(coinRecordInfo);
    }


    public  QueryResult<CoinRecordInfo> getCoinRecordListByPage(int pageIndex, int pageSize, String starttime, String endtime,  Integer ownertype,  String ownerid)
    {
        StringBuffer coinsql = new StringBuffer();
        coinsql.append(" from CouponInfo where 1=1 ");
        if (!CommonUtils.isEmptyString(starttime)) {
            coinsql.append(" and addtime > '" + starttime + "'");
        }

        if (!CommonUtils.isEmptyString(endtime)) {
            coinsql.append(" and addtime <= '" + endtime + " 23:59:59'");
        }
        if (ownertype != null) {
            coinsql.append(" and ownertype = " + ownertype);
            if (ownertype == 1) {
                if (!CommonUtils.isEmptyString(ownerid)) {
                    coinsql.append(" and ownerid in ( select schoolid from DriveSchoolInfo where name like '%" + ownerid + "%')");
                }
            } else if (ownertype == 2) {
                if (!CommonUtils.isEmptyString(ownerid)) {
                    coinsql.append(" and ownerid in ( select coachid from CuserInfo where realname like '%" + ownerid + "%')");
                }
            }
        }
        List<CoinRecordInfo> coinRecordList = (List<CoinRecordInfo>) dataDao.pageQueryViaParam(coinsql.toString() + " order by addtime desc", pageSize, pageIndex, null);

        String counthql = coinsql.insert(0, " select count(*) ").toString();
        long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        QueryResult<CoinRecordInfo> result = new QueryResult<CoinRecordInfo>(coinRecordList, count);
        return result;
    }
}
