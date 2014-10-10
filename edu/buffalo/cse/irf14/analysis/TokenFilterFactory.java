/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;


/**
 * Factory class for instantiating a given TokenFilter
 * 
 * @author nikhillo
 *
 */
public class TokenFilterFactory {
	/**
	 * Static method to return an instance of the factory class. Usually factory
	 * classes are defined as singletons, i.e. only one instance of the class
	 * exists at any instance. This is usually achieved by defining a private
	 * static instance that is initialized by the "private" constructor. On the
	 * method being called, you return the static instance. This allows you to
	 * reuse expensive objects that you may create during instantiation
	 * 
	 * @return An instance of the factory
	 */
	private static TokenFilterFactory tFactory = null;

	private TokenFilterFactory() {

	}

	public static TokenFilterFactory getInstance() {
		// TODO : YOU MUST IMPLEMENT THIS METHOD
		if (tFactory == null) {
			tFactory = new TokenFilterFactory();
		}
		return tFactory;
	}

	/**
	 * Returns a fully constructed {@link TokenFilter} instance for a given
	 * {@link TokenFilterType} type
	 * 
	 * @param type
	 *            : The {@link TokenFilterType} for which the
	 *            {@link TokenFilter} is requested
	 * @param stream
	 *            : The TokenStream instance to be wrapped
	 * @return The built {@link TokenFilter} instance
	 */
	public TokenFilter getFilterByType(TokenFilterType type, TokenStream stream) {
		// TODO : YOU MUST IMPLEMENT THIS METHOD

		TokenFilter tokenFilterObj = null;

		switch (type) {
		case SYMBOL: {
			tokenFilterObj = new TokenFilterSymbol(stream);
			break;
		}
		case DATE: {
			tokenFilterObj = new TokenFilterDates(stream);
			break;
		}
		case NUMERIC: {
			tokenFilterObj = new TokenFilterNumbers(stream);
			break;
		}
		case CAPITALIZATION: {
			tokenFilterObj = new TokenFilterCapitalization(stream);
			break;
		}
		case STOPWORD: {
			tokenFilterObj = new TokenFilterStopwords(stream);
			break;
		}
		case STEMMER: {
			tokenFilterObj = new TokenFilterStemmer(stream);
			break;
		}
		case ACCENT: {
			tokenFilterObj = new TokenFilterAccent(stream);
			break;
		}
		case SPECIALCHARS: {
			tokenFilterObj = new TokenFilterSpecialChars(stream);
			break;
		}

		default:
			break;
		}

		return tokenFilterObj;
	}
}
