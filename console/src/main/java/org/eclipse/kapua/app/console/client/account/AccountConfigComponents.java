/*******************************************************************************
 * Copyright (c) 2011, 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.client.account;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.kapua.app.console.client.device.button.ConfigDiscardButton;
import org.eclipse.kapua.app.console.client.device.button.ConfigSaveButton;
import org.eclipse.kapua.app.console.client.messages.ConsoleMessages;
import org.eclipse.kapua.app.console.client.resources.icons.IconSet;
import org.eclipse.kapua.app.console.client.resources.icons.KapuaIcon;
import org.eclipse.kapua.app.console.client.ui.button.RefreshButton;
import org.eclipse.kapua.app.console.client.ui.label.Label;
import org.eclipse.kapua.app.console.client.util.FailureHandler;
import org.eclipse.kapua.app.console.client.util.KapuaLoadListener;
import org.eclipse.kapua.app.console.shared.model.GwtConfigComponent;
import org.eclipse.kapua.app.console.shared.model.GwtSession;
import org.eclipse.kapua.app.console.shared.model.GwtXSRFToken;
import org.eclipse.kapua.app.console.shared.model.account.GwtAccount;
import org.eclipse.kapua.app.console.shared.service.GwtAccountService;
import org.eclipse.kapua.app.console.shared.service.GwtAccountServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtCredentialService;
import org.eclipse.kapua.app.console.shared.service.GwtCredentialServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtDataService;
import org.eclipse.kapua.app.console.shared.service.GwtDataServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtDeviceService;
import org.eclipse.kapua.app.console.shared.service.GwtDeviceServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtGroupService;
import org.eclipse.kapua.app.console.shared.service.GwtGroupServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtRoleService;
import org.eclipse.kapua.app.console.shared.service.GwtRoleServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtSecurityTokenService;
import org.eclipse.kapua.app.console.shared.service.GwtSecurityTokenServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtUserService;
import org.eclipse.kapua.app.console.shared.service.GwtUserServiceAsync;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelStringProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanelSelectionModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccountConfigComponents extends LayoutContainer {

    private static final ConsoleMessages MSGS = GWT.create(ConsoleMessages.class);

    private static final GwtSecurityTokenServiceAsync GWT_SECURITY_TOKEN_SERVICE = GWT.create(GwtSecurityTokenService.class);
    private static final GwtAccountServiceAsync GWT_ACCOUNT_SERVICE = GWT.create(GwtAccountService.class);
    private static final GwtCredentialServiceAsync GWT_CREDENTIAL_SERVICE = GWT.create(GwtCredentialService.class);
    private static final GwtGroupServiceAsync GWT_GROUP_SERVICE = GWT.create(GwtGroupService.class);
    private static final GwtRoleServiceAsync GWT_ROLE_SERVICE = GWT.create(GwtRoleService.class);
    private static final GwtDataServiceAsync GWT_DATA_SERVICE = GWT.create(GwtDataService.class);
    private static final GwtDeviceServiceAsync GWT_DEVICE_SERVICE = GWT.create(GwtDeviceService.class);
    private static final GwtUserServiceAsync GWT_USER_SERVICE = GWT.create(GwtUserService.class);

    private GwtSession currentSession;

    private boolean dirty;
    private boolean initialized;
    private GwtAccount selectedAccount;
    private AccountTabConfiguration tabConfig;

    private ToolBar toolBar;

    private Button refreshButton;
    private boolean refreshProcess;

    private Button apply;
    private Button reset;

    private ContentPanel configPanel;
    private AccountConfigPanel devConfPanel;
    private BorderLayoutData centerData;

    @SuppressWarnings("rawtypes")
    private BaseTreeLoader loader;
    private TreeStore<ModelData> treeStore;
    private TreePanel<ModelData> tree;

    private boolean resetProcess;

    private boolean applyProcess;

    private final AsyncCallback<Void> applyConfigCallback = new AsyncCallback<Void>() {

        public void onFailure(Throwable caught) {
            FailureHandler.handle(caught);
            dirty = true;
            refresh();
        }

        public void onSuccess(Void arg0) {
            dirty = true;
            refresh();
        }
    };

    AccountConfigComponents(GwtSession currentSession,
            AccountTabConfiguration tabConfig) {
        this.currentSession = currentSession;
        this.tabConfig = tabConfig;
        dirty = false;
        initialized = false;
    }

    public void setAccount(GwtAccount selectedAccount) {
        dirty = true;
        this.selectedAccount = selectedAccount;
    }

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setLayout(new FitLayout());
        setBorders(false);

        // init components
        initToolBar();
        initConfigPanel();

        ContentPanel accountConfigurationPanel = new ContentPanel();
        accountConfigurationPanel.setBorders(false);
        accountConfigurationPanel.setBodyBorder(false);
        accountConfigurationPanel.setHeaderVisible(false);
        accountConfigurationPanel.setLayout(new FitLayout());
        accountConfigurationPanel.setScrollMode(Scroll.AUTO);
        accountConfigurationPanel.setTopComponent(toolBar);
        accountConfigurationPanel.add(configPanel);

        add(accountConfigurationPanel);
        initialized = true;
    }

    private void initToolBar() {
        toolBar = new ToolBar();
        toolBar.setBorders(false);

        //
        // Refresh Button
        refreshButton = new RefreshButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!refreshProcess) {
                    refreshProcess = true;
                    refreshButton.setEnabled(false);

                    dirty = true;
                    refresh();

                    refreshButton.setEnabled(true);
                    refreshProcess = false;
                }
            }
        });

        refreshButton.setEnabled(true);
        toolBar.add(refreshButton);
        toolBar.add(new SeparatorToolItem());

        apply = new ConfigSaveButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!applyProcess) {
                    applyProcess = true;
                    apply.setEnabled(false);

                    apply();

                    apply.setEnabled(true);
                    applyProcess = false;
                }
            }
        });

        reset = new ConfigDiscardButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!resetProcess) {
                    resetProcess = true;
                    reset.setEnabled(false);

                    reset();

                    reset.setEnabled(true);
                    resetProcess = false;
                }
            }
        });

        apply.setEnabled(false);
        reset.setEnabled(false);

        toolBar.add(apply);
        toolBar.add(new SeparatorToolItem());
        toolBar.add(reset);
    }

    @SuppressWarnings("unchecked")
    private void initConfigPanel() {
        configPanel = new ContentPanel();
        configPanel.setBorders(false);
        configPanel.setBodyBorder(false);
        configPanel.setHeaderVisible(false);
        configPanel.setStyleAttribute("background-color", "white");
        configPanel.setScrollMode(Scroll.AUTO);

        BorderLayout borderLayout = new BorderLayout();
        configPanel.setLayout(borderLayout);

        // center
        centerData = new BorderLayoutData(LayoutRegion.CENTER);
        centerData.setMargins(new Margins(0));

        // west
        BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 200);
        westData.setSplit(true);
        westData.setCollapsible(true);
        westData.setMargins(new Margins(0, 5, 0, 0));

        // loader and store
        RpcProxy<List<GwtConfigComponent>> proxy = new RpcProxy<List<GwtConfigComponent>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<List<GwtConfigComponent>> callback) {
                if (selectedAccount != null) {
                    tree.mask(MSGS.loading());
                    GWT_ACCOUNT_SERVICE.findServiceConfigurations(selectedAccount.getId(), callback);
                    dirty = false;
                }
            }
        };

        loader = new BaseTreeLoader<GwtConfigComponent>(proxy);
        loader.addLoadListener(new DataLoadListener());

        treeStore = new TreeStore<ModelData>(loader);

        tree = new TreePanel<ModelData>(treeStore);
        tree.setWidth(200);
        tree.setDisplayProperty("componentName");
        tree.setBorders(true);
        tree.setLabelProvider(modelStringProvider);
        tree.setAutoSelect(true);
        tree.setStyleAttribute("background-color", "white");

        configPanel.add(tree, westData);

        //
        // Selection Listener for the component
        // make sure the form is not dirty before switching.
        tree.getSelectionModel().addListener(Events.BeforeSelect, new Listener<BaseEvent>() {

            @SuppressWarnings("rawtypes")
            @Override
            public void handleEvent(BaseEvent be) {

                SelectionEvent<ModelData> se = (SelectionEvent<ModelData>) be;

                final GwtConfigComponent componentToSwitchTo = (GwtConfigComponent) se.getModel();
                if (devConfPanel != null && devConfPanel.isDirty()) {

                    // cancel the event first
                    be.setCancelled(true);

                    // need to reselect the current entry
                    // as the BeforeSelect event cleared it
                    // we need to do this without raising events
                    TreePanelSelectionModel selectionModel = tree.getSelectionModel();
                    selectionModel.setFiresEvents(false);
                    selectionModel.select(false, devConfPanel.getConfiguration());
                    selectionModel.setFiresEvents(true);

                    // ask for confirmation before switching
                    MessageBox.confirm(MSGS.confirm(),
                            MSGS.deviceConfigDirty(),
                            new Listener<MessageBoxEvent>() {

                                public void handleEvent(MessageBoxEvent ce) {
                                    // if confirmed, delete
                                    Dialog dialog = ce.getDialog();
                                    if (dialog.yesText.equals(ce.getButtonClicked().getText())) {
                                        devConfPanel.removeFromParent();
                                        devConfPanel = null;
                                        tree.getSelectionModel().select(false, componentToSwitchTo);
                                    }
                                }
                            });
                } else {
                    refreshConfigPanel(componentToSwitchTo);

                    // this is needed to select the item in the Tree
                    // Temporarly disable the firing of the selection events
                    // to avoid an infinite loop as BeforeSelect would be invoked again.
                    TreePanelSelectionModel selectionModel = tree.getSelectionModel();
                    selectionModel.setFiresEvents(false);
                    selectionModel.select(false, componentToSwitchTo);

                    // renable firing of the events
                    selectionModel.setFiresEvents(true);
                }
            }
        });
    }

    public void refresh() {
        if (dirty && initialized) {

            // clear the tree and disable the toolbar
            apply.setEnabled(false);
            reset.setEnabled(false);
            refreshButton.setEnabled(false);

            treeStore.removeAll();

            // clear the panel
            if (devConfPanel != null) {
                devConfPanel.removeAll();
                devConfPanel.removeFromParent();
                devConfPanel = null;
                configPanel.layout();
            }

            loader.load();
        }
    }

    private void refreshConfigPanel(GwtConfigComponent configComponent) {
        apply.setEnabled(false);
        reset.setEnabled(false);

        if (devConfPanel != null) {
            devConfPanel.removeFromParent();
        }
        if (configComponent != null) {

            devConfPanel = new AccountConfigPanel(configComponent, currentSession, tabConfig.getSelectedEntity());
            devConfPanel.addListener(Events.Change, new Listener<BaseEvent>() {

                @Override
                public void handleEvent(BaseEvent be) {
                    apply.setEnabled(true);
                    reset.setEnabled(true);
                }
            });
            configPanel.add(devConfPanel, centerData);
            configPanel.layout();
        }
    }

    private void apply() {
        if (!devConfPanel.isValid()) {
            MessageBox mb = new MessageBox();
            mb.setIcon(MessageBox.ERROR);
            mb.setMessage(MSGS.deviceConfigError());
            mb.show();
            return;
        }

        // ask for confirmation
        String componentName = devConfPanel.getConfiguration().getComponentName();
        String message = MSGS.deviceConfigConfirmation(componentName);

        MessageBox.confirm(MSGS.confirm(),
                message,
                new Listener<MessageBoxEvent>() {

                    public void handleEvent(MessageBoxEvent ce) {

                        // if confirmed, push the update
                        // if confirmed, delete
                        Dialog dialog = ce.getDialog();
                        if (dialog.yesText.equals(ce.getButtonClicked().getText())) {

                            // mark the whole config panel dirty and for reload
                            tabConfig.setEntity(selectedAccount);

                            devConfPanel.mask(MSGS.applying());
                            tree.mask();
                            apply.setEnabled(false);
                            reset.setEnabled(false);
                            refreshButton.setEnabled(false);

                            //
                            // Getting XSRF token
                            GWT_SECURITY_TOKEN_SERVICE.generateSecurityToken(new AsyncCallback<GwtXSRFToken>() {

                                @Override
                                public void onFailure(Throwable ex) {
                                    FailureHandler.handle(ex);
                                }

                                @Override
                                public void onSuccess(GwtXSRFToken token) {
                                    final GwtConfigComponent configComponent = devConfPanel.getUpdatedConfiguration();

                                    if ("AccountService".equals(configComponent.getComponentName())) {
                                        GWT_ACCOUNT_SERVICE.updateComponentConfiguration(token, selectedAccount.getId(), selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("CredentialService".equals(configComponent.getComponentName())) {
                                        GWT_CREDENTIAL_SERVICE.updateComponentConfiguration(token, selectedAccount.getId(), selectedAccount.getParentAccountId(), configComponent,
                                                applyConfigCallback);
                                    } else if ("DeviceRegistryService".equals(configComponent.getComponentName())) {
                                        GWT_DEVICE_SERVICE.updateComponentConfiguration(token, selectedAccount.getId(), selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("GroupService".equals(configComponent.getComponentName())) {
                                        GWT_GROUP_SERVICE.updateComponentConfiguration(token, selectedAccount.getId(), selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("RoleService".equals(configComponent.getComponentName())) {
                                        GWT_ROLE_SERVICE.updateComponentConfiguration(token, selectedAccount.getId(), selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("MessageStoreService".equals(configComponent.getComponentName())) {
                                        GWT_DATA_SERVICE.updateComponentConfiguration(token, selectedAccount.getId(), selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("UserService".equals(configComponent.getComponentName())) {
                                        GWT_USER_SERVICE.updateComponentConfiguration(token, selectedAccount.getId(), selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    }
                                }
                            });

                            // start the configuration update
                        }
                    }
                });
    }

    public void reset() {
        final GwtConfigComponent comp = (GwtConfigComponent) tree.getSelectionModel().getSelectedItem();
        if (devConfPanel != null && comp != null && devConfPanel.isDirty()) {
            MessageBox.confirm(MSGS.confirm(),
                    MSGS.deviceConfigDirty(),
                    new Listener<MessageBoxEvent>() {

                        public void handleEvent(MessageBoxEvent ce) {
                            // if confirmed, delete
                            Dialog dialog = ce.getDialog();
                            if (dialog.yesText.equals(ce.getButtonClicked().getText())) {
                                refreshConfigPanel(comp);
                            }
                        }
                    });
        }
    }

    private ModelStringProvider<ModelData> modelStringProvider = new ModelStringProvider<ModelData>() {

        @Override
        public String getStringValue(ModelData model, String property) {

            KapuaIcon kapuaIcon = null;
            if (model instanceof GwtConfigComponent) {
                String iconName = ((GwtConfigComponent) model).getComponentIcon();

                if (iconName != null) {
                    if (iconName.startsWith("BluetoothService")) {
                        kapuaIcon = new KapuaIcon(IconSet.BTC);
                    } else if (iconName.startsWith("CloudService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CLOUD);
                    } else if (iconName.startsWith("DiagnosticsService")) {
                        kapuaIcon = new KapuaIcon(IconSet.AMBULANCE);
                    } else if (iconName.startsWith("ClockService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CLOCK_O);
                    } else if (iconName.startsWith("DataService")) {
                        kapuaIcon = new KapuaIcon(IconSet.DATABASE);
                    } else if (iconName.startsWith("MqttDataTransport")) {
                        kapuaIcon = new KapuaIcon(IconSet.FORUMBEE);
                    } else if (iconName.startsWith("PositionService")) {
                        kapuaIcon = new KapuaIcon(IconSet.LOCATION_ARROW);
                    } else if (iconName.startsWith("WatchdogService")) {
                        kapuaIcon = new KapuaIcon(IconSet.HEARTBEAT);
                    } else if (iconName.startsWith("SslManagerService")) {
                        kapuaIcon = new KapuaIcon(IconSet.LOCK);
                    } else if (iconName.startsWith("VpnService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CONNECTDEVELOP);
                    } else if (iconName.startsWith("ProvisioningService")) {
                        kapuaIcon = new KapuaIcon(IconSet.EXCLAMATION_CIRCLE);
                    } else if (iconName.startsWith("CommandPasswordService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CHAIN);
                    } else if (iconName.startsWith("WebConsole")) {
                        kapuaIcon = new KapuaIcon(IconSet.LAPTOP);
                    } else if (iconName.startsWith("CommandService")) {
                        kapuaIcon = new KapuaIcon(IconSet.TERMINAL);
                    } else if (iconName.startsWith("DenaliService")) {
                        kapuaIcon = new KapuaIcon(IconSet.SPINNER);
                    } else {
                        kapuaIcon = new KapuaIcon(IconSet.PUZZLE_PIECE);
                    }
                }
            }

            Label label = new Label(((GwtConfigComponent) model).getComponentName(), kapuaIcon);

            return label.getText();
        }
    };

    // --------------------------------------------------------------------------------------
    //
    // Data Load Listener
    //
    // --------------------------------------------------------------------------------------

    private class DataLoadListener extends KapuaLoadListener {

        DataLoadListener() {
        }

        public void loaderLoad(LoadEvent le) {
            if (le.exception != null) {
                FailureHandler.handle(le.exception);
            }
            tree.unmask();
            refreshButton.setEnabled(true);
        }

        public void loaderLoadException(LoadEvent le) {

            if (le.exception != null) {
                FailureHandler.handle(le.exception);
            }

            List<ModelData> comps = new ArrayList<ModelData>();
            GwtConfigComponent comp = new GwtConfigComponent();
            comp.setId(MSGS.deviceNoDeviceSelected());
            comp.setName(MSGS.deviceNoComponents());
            comp.setDescription(MSGS.deviceNoConfigSupported());
            comps.add(comp);
            treeStore.removeAll();
            treeStore.add(comps, false);

            tree.unmask();
        }
    }
}
