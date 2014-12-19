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

import java.io.Serializable;
import java.net.URL;
import javax.annotation.Nonnull;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * @author marembo
 */
@Dependent
public class EstelService implements Serializable {

    private static final long serialVersionUID = 10394898376346L;

    private final ConfigurationService estelConfigurationService;
    private final EstelTprServices estelTprServices;

    @Inject
    public EstelService(@Nonnull final ConfigurationService estelConfigurationService) throws Exception {
        this.estelConfigurationService = checkNotNull(estelConfigurationService, "the estelConfigurationService must not be null");

        final String wsdlLocation = this.estelConfigurationService.getWsdlLocation();
        final EstelTprServicesService estelTprServicesService = new EstelTprServicesService(new URL(wsdlLocation));
        this.estelTprServices = estelTprServicesService.getEstelTprServices();
    }

    public WalletTransferInfoResponse wallettransfer(@Nonnull final WalletTransferInfoRequest walletTransferInfoRequest) {
        return estelTprServices.wallettransfer(checkNotNull(walletTransferInfoRequest, "the walletTransferInfoRequest must be specified"));
    }

    public TopupResponse getTopup(@Nonnull final TopupRequest topupRequest) {
        return estelTprServices.getTopup(checkNotNull(topupRequest, "the topupRequest must be specified"));
    }

    public TransHistoryResponse getTransHistory(@Nonnull final TransHistoryRequest historyRequest) {
        return estelTprServices.getTransHistory(checkNotNull(historyRequest, "the historyRequest must be specified"));
    }

    public BalanceResponse getBalance() {
        final BalanceRequest balanceRequest = new BalanceRequest();
        balanceRequest.setAgentCode(estelConfigurationService.getAgentCode());
        balanceRequest.setMpin(estelConfigurationService.getAuthenticationKey());

        return estelTprServices.getBalance(balanceRequest);
    }

}
