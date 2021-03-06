/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.client.device;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.kapua.app.console.client.device.button.BundleStartButton;
import org.eclipse.kapua.app.console.client.device.button.BundleStopButton;
import org.eclipse.kapua.app.console.client.messages.ConsoleMessages;
import org.eclipse.kapua.app.console.client.ui.button.RefreshButton;
import org.eclipse.kapua.app.console.client.util.FailureHandler;
import org.eclipse.kapua.app.console.client.util.KapuaLoadListener;
import org.eclipse.kapua.app.console.shared.model.GwtDevice;
import org.eclipse.kapua.app.console.shared.model.GwtSession;
import org.eclipse.kapua.app.console.shared.model.GwtXSRFToken;
import org.eclipse.kapua.app.console.shared.model.device.management.bundles.GwtBundle;
import org.eclipse.kapua.app.console.shared.service.GwtDeviceManagementService;
import org.eclipse.kapua.app.console.shared.service.GwtDeviceManagementServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtSecurityTokenService;
import org.eclipse.kapua.app.console.shared.service.GwtSecurityTokenServiceAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DeviceTabBundles extends LayoutContainer {

    private static final ConsoleMessages MSGS = GWT.create(ConsoleMessages.class);

    private final GwtDeviceManagementServiceAsync gwtDeviceManagementService = GWT.create(GwtDeviceManagementService.class);
    private final GwtSecurityTokenServiceAsync gwtXSRFService = GWT.create(GwtSecurityTokenService.class);

    private DeviceTabs deviceTabs;

    private boolean dirty;
    private boolean initialized;
    private GwtDevice selectedDevice;

    private ToolBar toolBar;

    private Button refreshButton;
    private Button startButton;
    private Button stopButton;

    private Grid<GwtBundle> grid;
    private ListStore<GwtBundle> store;
    private BaseListLoader<ListLoadResult<GwtBundle>> loader;

    protected boolean refreshProcess;

    public DeviceTabBundles(GwtSession currentSession,
            DeviceTabs deviceTabs) {
        this.deviceTabs = deviceTabs;
        dirty = true;
        initialized = false;
    }

    public void setDevice(GwtDevice selectedDevice) {
        dirty = true;
        this.selectedDevice = selectedDevice;
    }

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setLayout(new FitLayout());

        // init components
        initToolBar();
        initGrid();

        ContentPanel devicesBundlesPanel = new ContentPanel();
        devicesBundlesPanel.setBorders(false);
        devicesBundlesPanel.setBodyBorder(false);
        devicesBundlesPanel.setHeaderVisible(false);
        devicesBundlesPanel.setLayout(new FitLayout());
        devicesBundlesPanel.setScrollMode(Scroll.AUTO);
        devicesBundlesPanel.setTopComponent(toolBar);
        devicesBundlesPanel.add(grid);

        add(devicesBundlesPanel);
        initialized = true;
    }

    private void initToolBar() {
        toolBar = new ToolBar();

        //
        // Refresh Button
        refreshButton = new RefreshButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!refreshProcess) {
                    refreshProcess = true;

                    if (selectedDevice.isOnline()) {
                        toolBar.disable();
                        dirty = true;
                        refresh();

                        refreshProcess = false;
                    } else {
                        MessageBox.alert(MSGS.dialogAlerts(), MSGS.deviceOffline(),
                                new Listener<MessageBoxEvent>() {

                                    @Override
                                    public void handleEvent(MessageBoxEvent be) {
                                        grid.unmask();

                                        refreshProcess = false;
                                    }
                                });
                    }
                }
            }
        });

        refreshButton.setEnabled(true);
        toolBar.add(refreshButton);
        toolBar.add(new SeparatorToolItem());

        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                FailureHandler.handle(caught);
                dirty = true;
            }

            public void onSuccess(Void arg0) {
                // mark this panel dirty and also all the other pier panels
                deviceTabs.setDevice(selectedDevice);
                dirty = true;
                refresh();
            }
        };

        //
        // Start Button
        startButton = new BundleStartButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (selectedDevice.isOnline()) {
                    toolBar.disable();
                    grid.mask(MSGS.loading());

                    //
                    // Getting XSRF token
                    gwtXSRFService.generateSecurityToken(new AsyncCallback<GwtXSRFToken>() {

                        @Override
                        public void onFailure(Throwable ex) {
                            FailureHandler.handle(ex);
                        }

                        @Override
                        public void onSuccess(GwtXSRFToken token) {
                            gwtDeviceManagementService.startBundle(token,
                                    selectedDevice,
                                    grid.getSelectionModel().getSelectedItem(),
                                    callback);
                        }
                    });
                } else {
                    MessageBox.alert(MSGS.dialogAlerts(), MSGS.deviceOffline(),
                            new Listener<MessageBoxEvent>() {

                                @Override
                                public void handleEvent(MessageBoxEvent be) {
                                    grid.unmask();
                                }
                            });
                }
            }
        });
        startButton.setEnabled(true);
        toolBar.add(startButton);
        toolBar.add(new SeparatorToolItem());

        //
        // Stop Button
        stopButton = new BundleStopButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (selectedDevice.isOnline()) {
                    final GwtBundle gwtBundle = grid.getSelectionModel().getSelectedItem();
                    String bundleName = gwtBundle.getName();
                    MessageBox.confirm(MSGS.confirm(),
                            MSGS.deviceStopBundle(bundleName),
                            new Listener<MessageBoxEvent>() {

                                public void handleEvent(MessageBoxEvent ce) {
                                    // if confirmed, stop
                                    Dialog dialog = ce.getDialog();
                                    if (dialog.yesText.equals(ce.getButtonClicked().getText())) {
                                        toolBar.disable();
                                        grid.mask(MSGS.loading());
                                        //
                                        // Getting XSRF token
                                        gwtXSRFService.generateSecurityToken(new AsyncCallback<GwtXSRFToken>() {

                                            @Override
                                            public void onFailure(Throwable ex) {
                                                FailureHandler.handle(ex);
                                            }

                                            @Override
                                            public void onSuccess(GwtXSRFToken token) {
                                                gwtDeviceManagementService.stopBundle(token,
                                                        selectedDevice,
                                                        gwtBundle,
                                                        callback);
                                            }
                                        });
                                    }
                                }
                            });
                } else {
                    MessageBox.alert(MSGS.dialogAlerts(), MSGS.deviceOffline(),
                            new Listener<MessageBoxEvent>() {

                                @Override
                                public void handleEvent(MessageBoxEvent be) {
                                    grid.unmask();
                                }
                            });
                }
            }
        });
        stopButton.setEnabled(true);
        toolBar.add(stopButton);
        toolBar.add(new SeparatorToolItem());

        toolBar.disable();
    }

    private void initGrid() {
        RpcProxy<ListLoadResult<GwtBundle>> proxy = new RpcProxy<ListLoadResult<GwtBundle>>() {

            @Override
            protected void load(Object loadConfig, final AsyncCallback<ListLoadResult<GwtBundle>> callback) {
                if (selectedDevice != null) {
                    if (selectedDevice.isOnline()) {
                        gwtDeviceManagementService.findBundles(selectedDevice, callback);
                    } else {
                        grid.getStore().removeAll();
                        grid.unmask();
                    }
                }
            }
        };
        loader = new BaseListLoader<ListLoadResult<GwtBundle>>(proxy);
        loader.addLoadListener(new DataLoadListener());

        store = new ListStore<GwtBundle>(loader);

        ColumnConfig id = new ColumnConfig("id", MSGS.deviceBndId(), 10);
        ColumnConfig name = new ColumnConfig("name", MSGS.deviceBndName(), 50);
        ColumnConfig status = new ColumnConfig("statusLoc", MSGS.deviceBndState(), 20);
        ColumnConfig version = new ColumnConfig("version", MSGS.deviceBndVersion(), 20);

        List<ColumnConfig> config = new ArrayList<ColumnConfig>();
        config.add(id);
        config.add(name);
        config.add(status);
        config.add(version);

        ColumnModel cm = new ColumnModel(config);

        GridView view = new GridView();
        view.setForceFit(true);
        view.setEmptyText(MSGS.deviceNoDeviceSelectedOrOffline());

        GridSelectionModel<GwtBundle> selectionModel = new GridSelectionModel<GwtBundle>();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        grid = new Grid<GwtBundle>(store, cm);
        grid.setView(view);
        grid.setBorders(false);
        grid.setLoadMask(true);
        grid.setStripeRows(true);
        grid.setSelectionModel(selectionModel);
        grid.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<GwtBundle>() {

            @Override
            public void selectionChanged(SelectionChangedEvent<GwtBundle> se) {
                if (grid.getSelectionModel().getSelectedItem() != null) {
                    GwtBundle selectedBundle = grid.getSelectionModel().getSelectedItem();
                    if ("bndActive".equals(selectedBundle.getStatus())) {
                        startButton.disable();
                        stopButton.enable();
                    } else {
                        stopButton.disable();
                        startButton.enable();
                    }
                }
            }
        });
    }

    public void refresh() {
        if (dirty && initialized) {

            dirty = false;
            if (selectedDevice != null) {
                loader.load();
                toolBar.enable();
                startButton.disable();
                stopButton.disable();
            } else {
                grid.getStore().removeAll();
                toolBar.disable();
            }
        }
    }

    public void reload() {
        if (selectedDevice != null) {
            loader.load();
        }
    }

    // --------------------------------------------------------------------------------------
    //
    // Data Load Listener
    //
    // --------------------------------------------------------------------------------------

    private class DataLoadListener extends KapuaLoadListener {

        public DataLoadListener() {
        }

        public void loaderBeforeLoad(LoadEvent le) {
            grid.mask(MSGS.loading());
        }

        public void loaderLoad(LoadEvent le) {
            if (le.exception != null) {
                FailureHandler.handle(le.exception);
            }
            startButton.disable();
            stopButton.disable();
            grid.unmask();
        }

        public void loaderLoadException(LoadEvent le) {

            if (le.exception != null) {
                FailureHandler.handle(le.exception);
            }
            startButton.disable();
            stopButton.disable();
            grid.unmask();
        }
    }
}
