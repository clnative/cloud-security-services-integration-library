/**
 * SPDX-FileCopyrightText: 2018-2023 SAP SE or an SAP affiliate company and Cloud Security Client Java contributors
 * <p>
 * SPDX-License-Identifier: Apache-2.0
 */
package com.sap.cloud.security.xsuaa.client;

import com.sap.cloud.security.xsuaa.http.HttpHeaders;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Service that targets Identity service (xsuaa and identity) to request Json
 * Web Keys.
 */
public interface OAuth2TokenKeyService {

	/**
	 * Requests token web key set from Xsuaa OAuth Server.
	 *
	 * @param tokenKeysEndpointUri
	 *            the token endpoint URI (jku).
	 * @param tenantId
	 *            the unique identifier of the tenant. Obligatory parameter in context of
	 *            multi-tenant IAS applications to make sure that the tenant id
	 *            belongs to the IAS tenant.
	 * @return list of JSON Web Token (JWT) keys as
	 *         JSON string.
	 * @throws OAuth2ServiceException
	 *             in case of an error during the http request.
	 */
	default String retrieveTokenKeys(@Nonnull URI tokenKeysEndpointUri, @Nullable String tenantId) throws OAuth2ServiceException {
		return retrieveTokenKeys(tokenKeysEndpointUri, Collections.singletonMap(HttpHeaders.X_APP_TID, tenantId));
	}

	/**
	 * @deprecated Use {@link OAuth2TokenKeyService#retrieveTokenKeys(URI, Map)} instead
	 * Requests token web key set from IAS OAuth Server.
	 *
	 * @param tokenKeysEndpointUri
	 *            the token endpoint URI (jku).
	 * @param tenantId
	 *            the unique identifier of the tenant. Obligatory parameter in context of
	 *            multi-tenant IAS applications to make sure that the tenant id
	 *            belongs to the IAS tenant.
	 * @param clientId
	 * 				clientId from the service binding
	 * @return list of JSON Web Token (JWT) keys as
	 *         JSON string.
	 * @throws OAuth2ServiceException
	 *             in case of an error during the http request.
	 */
	@Deprecated
	default String retrieveTokenKeys(@Nonnull URI tokenKeysEndpointUri, @Nullable String tenantId, @Nullable String clientId) throws OAuth2ServiceException {
		Map<String, String> params = new HashMap<>(2, 1);
		params.put(HttpHeaders.X_APP_TID, tenantId);
		params.put(HttpHeaders.X_CLIENT_ID, clientId);

		return retrieveTokenKeys(tokenKeysEndpointUri, params);
	}

	/**
	 * Retrieves the JWKS (JSON Web Key Set) from the OAuth2 Server.
	 *
	 * @param tokenKeysEndpointUri the JWKS endpoint URI.
	 * @param params additional header parameters that are sent along with the request. Use constants from {@link HttpHeaders} for the header keys.
	 * @return a JWKS in JSON format.
	 * @throws OAuth2ServiceException in case of an error during the http request.
	 */
	String retrieveTokenKeys(@Nonnull URI tokenKeysEndpointUri, Map<String, String> params) throws OAuth2ServiceException;
}
