package org.hni.admin.service.converter;

import java.util.ArrayList;
import java.util.Collection;

import org.hni.admin.service.UserServiceController;
import org.hni.admin.service.dto.HniServicesDto;
import org.hni.organization.om.HniServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HNIConverter {
	private static final Logger logger = LoggerFactory.getLogger(HNIConverter.class);
	
	public static Collection<HniServicesDto> convertToServiceDtos(
			Collection<HniServices> hniServices) {
		
		Collection<HniServicesDto> hniServicesDtoList = new ArrayList<>();
		for(HniServices hniService : hniServices){
			HniServicesDto hniServiceDto = new HniServicesDto();
			hniServiceDto.setServiceName(hniService.getServiceName());
			hniServiceDto.setServicePath(hniService.getServicePath());
			hniServiceDto.setServiceImg(hniService.getServiceImg());
			hniServiceDto.setActive(hniService.getActive());
			
			hniServicesDtoList.add(hniServiceDto);
		}
		return hniServicesDtoList;
	}

}
