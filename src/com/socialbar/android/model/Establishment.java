package com.socialbar.android.model;

import java.util.List;

/**
 * Interface <code>Establishment</code>.
 */
public interface Establishment {
	/**
	 * tipo de estalecimento
	 * 
	 * @author Felipe
	 * 
	 */
	enum Type {
		COMIDA, BEBIDA, SORVETE, RODIZIO
	};

	// leitura
	/** Getter for property "latitude" */
	double getLatitude();

	/** Getter for property "longitude" */
	double getLongitude();

	// informações
	/**
	 * pegar id
	 * 
	 * @return
	 */
	String getID();

	/**
	 * pegar nome
	 * 
	 * @return
	 */
	String getName();

	/**
	 * pegar texto de descricao
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * pegar setar data da ultima modificacao
	 * 
	 * @param miliseconds
	 */
	void setLastModified(long miliseconds);

	/**
	 * pegar data da ultima modificacao
	 * 
	 * @return
	 */
	long getLastModified();

	/** pegar o numero de pessoas no estabelecimento */
	int getPeople();

	/**
	 * pegar endereco
	 * 
	 * @return
	 */
	String getAddress();

	/**
	 * pegar telefone
	 * 
	 * @return
	 */
	String getPhoneNumber();

	/**
	 * pegar todas as caracteristicas do estabelecimento
	 * 
	 * @return
	 */
	<T extends Feature> List<T> getFeatures();

	/**
	 * pegar todos os produtos com preco do estabelecimento
	 * 
	 * @return
	 */
	<T extends Product> List<T> getProducts();

	// customizações do assinante
	/**
	 * pegar icone/logotipo cusmizacao do assinante
	 * 
	 * @return
	 */
	byte[] getIconImage();

	/**
	 * pegar a imagem de bemvindo do estabelecimento
	 * 
	 * @return
	 */
	byte[] getWelcomeImage();

	/**
	 * adicionar gostei pelo usuario
	 * 
	 * @param user
	 */
	void addLikeIt(Object user);

	/**
	 * remover gostei pelo usuario
	 * 
	 * @param user
	 */
	void removeLikeIt(Object user);

	/**
	 * adicionar comentario
	 * 
	 * @param user
	 */
	void addComment(Object user);

	/**
	 * remover comentario
	 * 
	 * @param user
	 */
	void removeComment(Object user);

	/*
	 * O checkin é feito manualmente pelo usuário, porem o checkout é feito
	 * automaticametequando o usário se afasta de estabelecimento.
	 */
	/** Setter for establishment's <b>number of people</b>. */
	boolean checkIn();

	/**
	 * setar nome para o estabelecimento
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * setar endereco
	 * 
	 * @param address
	 */
	void setAddress(String address);

	/**
	 * setar telefone
	 * 
	 * @param phoneNumber
	 */
	void setPhoneNumber(String phoneNumber);

	/**
	 * setar latitude do estabelecimento
	 * 
	 * @param latitude
	 */
	void setLatitude(double latitude);

	/**
	 * setar longitude do estabelecimento
	 * 
	 * @param longitude
	 */
	void setLongitude(double longitude);

	/**
	 * vincular produto ao estabelecimeto
	 * 
	 * @param product
	 * @return
	 */
	boolean addProduct(Product product);

	/**
	 * desvincular um produto ao estabelecimento
	 * 
	 * @param product
	 * @return
	 */
	boolean removeProduct(Product product);

	/**
	 * adicionar uma caracteristica ao estabelecimento
	 * 
	 * @param feature
	 * @return
	 */
	boolean addFeature(Feature feature);

	/**
	 * remover uma caracteristica do estabelecimento
	 * 
	 * @param feature
	 * @return
	 */
	boolean removeFeature(Feature feature);

	/**
	 * verificar se o estabelecimento esta setado como favorito
	 * 
	 * @return
	 */
	boolean isFavorite();

	/**
	 * setar o estabelecimento como favorito
	 * 
	 * @param favorite
	 */
	void setFavorite(boolean favorite);

	/**
	 * pegar data de criacao do estabelecimento no sistema
	 * 
	 * @return
	 */
	long getCreatedAt();
}
