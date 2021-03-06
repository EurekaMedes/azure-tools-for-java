package com.microsoft.intellij.runner.webapp.webappconfig.slimui.creation;

import com.microsoft.azuretools.core.mvp.model.AzureMvpModel;
import com.microsoft.azuretools.core.mvp.model.webapp.AzureWebAppMvpModel;
import com.microsoft.azuretools.core.mvp.ui.base.MvpPresenter;
import com.microsoft.tooling.msservices.components.DefaultLoader;
import rx.Observable;

public class WebAppCreationViewPresenter<V extends WebAppCreationMvpView> extends MvpPresenter<V> {

    private static final String CANNOT_LIST_RES_GRP = "Failed to list resource groups.";
    private static final String CANNOT_LIST_APP_SERVICE_PLAN = "Failed to list app service plan.";
    private static final String CANNOT_LIST_SUBSCRIPTION = "Failed to list subscriptions.";
    private static final String CANNOT_LIST_LOCATION = "Failed to list locations.";
    private static final String CANNOT_LIST_PRICING_TIER = "Failed to list pricing tier.";

    /**
     * Load subscriptions from model.
     */
    public void onLoadSubscription() {
        Observable.fromCallable(() -> AzureMvpModel.getInstance().getSelectedSubscriptions())
            .subscribeOn(getSchedulerProvider().io())
            .subscribe(subscriptions -> DefaultLoader.getIdeHelper().invokeLater(() -> {
                if (isViewDetached()) {
                    return;
                }
                getMvpView().fillSubscription(subscriptions);
            }), e -> errorHandler(CANNOT_LIST_SUBSCRIPTION, (Exception) e));
    }

    /**
     * Load resource groups from model.
     */
    public void onLoadResourceGroups(String sid) {
        Observable.fromCallable(() -> AzureMvpModel.getInstance().getResourceGroupsBySubscriptionId(sid))
            .subscribeOn(getSchedulerProvider().io())
            .subscribe(resourceGroups -> DefaultLoader.getIdeHelper().invokeLater(() -> {
                if (isViewDetached()) {
                    return;
                }
                getMvpView().fillResourceGroup(resourceGroups);
            }), e -> errorHandler(CANNOT_LIST_RES_GRP, (Exception) e));
    }

    /**
     * Load app service plan from model.
     */
    public void onLoadAppServicePlan(String sid) {
        Observable.fromCallable(() -> AzureWebAppMvpModel.getInstance().listAppServicePlanBySubscriptionId(sid))
            .subscribeOn(getSchedulerProvider().io())
            .subscribe(appServicePlans -> DefaultLoader.getIdeHelper().invokeLater(() -> {
                if (isViewDetached()) {
                    return;
                }
                getMvpView().fillAppServicePlan(appServicePlans);
            }), e -> errorHandler(CANNOT_LIST_APP_SERVICE_PLAN, (Exception) e));
    }

    /**
     * Load locations from model.
     */
    public void onLoadLocation(String sid) {
        Observable.fromCallable(() -> AzureMvpModel.getInstance().listLocationsBySubscriptionId(sid))
            .subscribeOn(getSchedulerProvider().io())
            .subscribe(locations -> DefaultLoader.getIdeHelper().invokeLater(() -> {
                if (isViewDetached()) {
                    return;
                }
                getMvpView().fillLocation(locations);
            }), e -> errorHandler(CANNOT_LIST_LOCATION, (Exception) e));
    }

    /**
     * Load pricing tier from model.
     */
    public void onLoadPricingTier() {
        try {
            getMvpView().fillPricingTier(AzureMvpModel.getInstance().listPricingTier());
        } catch (IllegalAccessException e) {
            errorHandler(CANNOT_LIST_PRICING_TIER, e);
        }
    }

    /**
     * Load web containers from model.
     */
    public void onLoadWebContainer() {
        getMvpView().fillWebContainer(AzureWebAppMvpModel.getInstance().listWebContainers());
    }

    /**
     * Load Java versions from model.
     */
    public void onLoadJavaVersions() {
        getMvpView().fillJdkVersion(AzureWebAppMvpModel.getInstance().listJdks());
    }

    /**
     * Load Java Linux runtimes from model.
     */
    public void onLoadLinuxRuntimes() {
        getMvpView().fillLinuxRuntime(AzureWebAppMvpModel.getInstance().getLinuxRuntimes());
    }

    private void errorHandler(String msg, Exception e) {
        DefaultLoader.getIdeHelper().invokeLater(() -> {
            if (isViewDetached()) {
                return;
            }
            getMvpView().onErrorWithException(msg, e);
        });
    }
}
