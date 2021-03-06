/*******************************************************************************
 * Copyright (c) 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.server;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.kapua.app.console.server.util.KapuaExceptionHandler;
import org.eclipse.kapua.app.console.shared.GwtKapuaException;
import org.eclipse.kapua.app.console.shared.model.GwtXSRFToken;
import org.eclipse.kapua.app.console.shared.model.device.management.assets.GwtDeviceAsset;
import org.eclipse.kapua.app.console.shared.model.device.management.assets.GwtDeviceAssetChannel;
import org.eclipse.kapua.app.console.shared.model.device.management.assets.GwtDeviceAssets;
import org.eclipse.kapua.app.console.shared.service.GwtDeviceAssetService;
import org.eclipse.kapua.app.console.shared.util.GwtKapuaModelConverter;
import org.eclipse.kapua.app.console.shared.util.KapuaGwtModelConverter;
import org.eclipse.kapua.locator.KapuaLocator;
import org.eclipse.kapua.model.id.KapuaId;
import org.eclipse.kapua.service.device.management.asset.DeviceAsset;
import org.eclipse.kapua.service.device.management.asset.DeviceAssetChannel;
import org.eclipse.kapua.service.device.management.asset.DeviceAssetChannelMode;
import org.eclipse.kapua.service.device.management.asset.DeviceAssetManagementService;
import org.eclipse.kapua.service.device.management.asset.DeviceAssets;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;

public class GwtDeviceAssetServiceImpl extends KapuaRemoteServiceServlet implements GwtDeviceAssetService {

    private static final long serialVersionUID = -7140054857264920053L;
    private final KapuaLocator locator = KapuaLocator.getInstance();
    private final DeviceAssetManagementService assetService = locator.getService(DeviceAssetManagementService.class);

    @Override
    public List<GwtDeviceAsset> read(String scopeId, String deviceId, GwtDeviceAssets deviceAssets) throws GwtKapuaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(GwtXSRFToken xsrfToken, String scopeIdString, String deviceIdString, GwtDeviceAsset gwtAsset) throws GwtKapuaException {
        //
        // Checking validity of the given XSRF Token
        checkXSRFToken(xsrfToken);

        GwtDeviceAssets gwtAssets = new GwtDeviceAssets();
        List<GwtDeviceAsset> gwtAssetsList = new ArrayList<GwtDeviceAsset>();
        gwtAssetsList.add(gwtAsset);
        gwtAssets.setAssets(gwtAssetsList);
        try {
            KapuaId scopeId = GwtKapuaModelConverter.convert(scopeIdString);
            KapuaId deviceId = GwtKapuaModelConverter.convert(deviceIdString);

            assetService.write(scopeId, deviceId, GwtKapuaModelConverter.convert(gwtAssets), null);
        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }
    }

    @Override
    public List<GwtDeviceAsset> get(PagingLoadConfig pagingLoadConfig, String scopeIdString, String deviceIdString, GwtDeviceAssets deviceAssets) throws GwtKapuaException {
        GwtDeviceAssets gwtAssets = new GwtDeviceAssets();
        try {
            KapuaId scopeId = GwtKapuaModelConverter.convert(scopeIdString);
            KapuaId deviceId = GwtKapuaModelConverter.convert(deviceIdString);
            DeviceAssets assetsMetadata = assetService.get(scopeId, deviceId, GwtKapuaModelConverter.convert(deviceAssets), null);
            DeviceAssets assetsValues = assetService.read(scopeId, deviceId, GwtKapuaModelConverter.convert(deviceAssets), null);

            for (int assetIndex = 0; assetIndex < assetsMetadata.getAssets().size(); assetIndex++) {
                DeviceAsset assetMetadata = assetsMetadata.getAssets().get(assetIndex);
                DeviceAsset assetValues = assetsValues.getAssets().get(assetIndex);
                for (DeviceAssetChannel channelMetadata : assetMetadata.getChannels()) {
                    if (channelMetadata.getMode().equals(DeviceAssetChannelMode.READ) || channelMetadata.getMode().equals(DeviceAssetChannelMode.READ_WRITE)) {
                        for (DeviceAssetChannel channelValue : assetValues.getChannels()) {
                            if (channelValue.getName().equals(channelMetadata.getName())) {
                                channelMetadata.setValue(channelValue.getValue());
                                break;
                            }
                        }
                    }
                }
            }

            gwtAssets = KapuaGwtModelConverter.convert(assetsMetadata);
        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }
        return gwtAssets.getAssets();
    }

    @Override
    public GwtDeviceAssetChannel trickGWT(GwtDeviceAssetChannel channel) {
        // TODO Auto-generated method stub
        return null;
    }

}
