/**
 * 
 */
package com.daoshun.guangda.serviceImpl;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.dao.DataDao;
import com.daoshun.guangda.pojo.FileInfo;
import com.daoshun.guangda.service.IBaseService;

/**
 * @author wangcl
 * 
 */
@Service("baseService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BaseServiceImpl implements IBaseService {

	@Resource
	public DataDao dataDao;

	@Override
	public String getFilePathById(long i) {
		if (i > 0) {
			FileInfo file = (FileInfo) dataDao.getObjectById(FileInfo.class, i);
			// 拼接文件访问路径
			if (file != null) {
				String path = CommonUtils.getFileRootUrl() + file.getFile_url() + file.getFile_name();
				return path.replace(File.separator, "/");
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public long uploadComplete(String fileurl, String filename) {
		FileInfo finfo = new FileInfo();
		finfo.setFile_name(filename);
		finfo.setFile_url(fileurl);
		dataDao.addObject(finfo);
		return finfo.getFile_id();
	}
	
	//求两个数组的交集   
	@Override
	   public String[] intersect(Object[] arr1, Object[] arr2) {
	       Map<String, Boolean> map = new HashMap<String, Boolean>();
	       LinkedList<String> list = new LinkedList<String>();   
	       for (Object str : arr1) {   
	           if (!map.containsKey(str)) {   
	               map.put(str.toString(), Boolean.FALSE);
	           }   
	       }   
	       for (Object str : arr2) {   
	           if (map.containsKey(str)) {   
	               map.put(str.toString(), Boolean.TRUE);   
	           }   
	       }
	       for (Entry<String, Boolean> e : map.entrySet()) {   
	           if (e.getValue().equals(Boolean.TRUE)) {   
	               list.add(e.getKey());   
	           }   
	       }
	       String[] result = {};
	       return list.toArray(result);   
	   }
	
	   //求两个数组的差集 
		@Override
	   public String[] minus(String[] arr1, String[] arr2) { 
	       LinkedList<String> list = new LinkedList<String>();   
	       LinkedList<String> history = new LinkedList<String>();   
	       String[] longerArr = arr1;   
	       String[] shorterArr = arr2;   
	       //找出较长的数组来减较短的数组   
	       if (arr1.length > arr2.length) {
	           longerArr = arr2;
	           shorterArr = arr1;   
	       }   
	       for (String str : longerArr) {   
	           if (!list.contains(str)) {   
	               list.add(str);   
	           }   
	       }   
	       for (String str : shorterArr) {   
	           if (list.contains(str)) {   
	               history.add(str);   
	               list.remove(str);   
	           } else {   
	               if (!history.contains(str)) {   
	                   list.add(str);   
	               }   
	           }   
	       }   
	       String[] result = {};   
	       return list.toArray(result);   
	   }      
}
