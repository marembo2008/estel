package com.afrocoin.estel.service;

import com.afrocoin.estel.config.ConfigurationService;
import com.estel.services.EstelTprServices;
import com.estel.services.EstelTprServicesService;
import com.estel.services.support.balance.BalanceRequest;
import com.estel.services.support.balance.BalanceResponse;
import com.estel.services.support.topup.TopupRequest;
import com.estel.services.support.topup.TopupResponse;
import com.estel.services.support.transhistory.TransHistoryRequest;
import com.estel.services.support.transhistory.TransHistoryResponse;
import com.estel.services.support.wallettransfer.WalletTransferInfoRequest;
import com.estel.services.support.wallettransfer.WalletTransferInfoResponse;
import com.google.common.base.Throwables;

import java.io.Serializable;
import java.net.URL;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 *
 * @author marembo
 */
@Dependent
public class EstelService implements Serializable {

    private static final long serialVersionUID = 10394898376346L;

    private final ConfigurationService estelConfigurationService;
    private EstelTprServices estelTprServices;

    @Inject
    public EstelService(@Nonnull final ConfigurationService estelConfigurationService) throws Exception {
        this.estelConfigurationService = checkNotNull(estelConfigurationService, "the estelConfigurationService must not be null");
    }

    @PostConstruct
    protected void initialize() {
        if (estelConfigurationService.isActive()) {
            try {
                final String wsdlLocation = this.estelConfigurationService.getWsdlLocation();
                final EstelTprServicesService estelTprServicesService = new EstelTprServicesService(new URL(wsdlLocation));
                this.estelTprServices = estelTprServicesService.getEstelTprServices();
            } catch (final Exception ex) {
                throw Throwables.propagate(ex);
            }
        }
    }

    public WalletTransferInfoResponse wallettransfer(@Nonnull final WalletTransferInfoRequest walletTransferInfoRequest) {
        checkState(estelConfigurationService.isActive(), "the estelservice has not been activated yet. Please activate in configservice");

        return estelTprServices.wallettransfer(checkNotNull(walletTransferInfoRequest, "the walletTransferInfoRequest must be specified"));
    }

    public TopupResponse getTopup(@Nonnull final TopupRequest topupRequest) {
        checkState(estelConfigurationService.isActive(), "the estelservice has not been activated yet. Please activate in configservice");

        return estelTprServices.getTopup(checkNotNull(topupRequest, "the topupRequest must be specified"));
    }

    public TransHistoryResponse getTransHistory(@Nonnull final TransHistoryRequest historyRequest) {
        checkState(estelConfigurationService.isActive(), "the estelservice has not been activated yet. Please activate in configservice");

        return estelTprServices.getTransHistory(checkNotNull(historyRequest, "the historyRequest must be specified"));
    }

    public BalanceResponse getBalance() {
        checkState(estelConfigurationService.isActive(), "the estelservice has not been activated yet. Please activate in configservice");

        final BalanceRequest balanceRequest = new BalanceRequest();
        balanceRequest.setAgentCode(estelConfigurationService.getAgentCode());
        balanceRequest.setMpin(estelConfigurationService.getAuthenticationKey());

        return estelTprServices.getBalance(balanceRequest);
    }

}
