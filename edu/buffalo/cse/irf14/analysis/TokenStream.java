/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.*;

/**
 * @author nikhillo Class that represents a stream of Tokens. All
 *         {@link Analyzer} and {@link TokenFilter} instances operate on this to
 *         implement their behavior
 */
public class TokenStream implements Iterator<Token> {

	/**
	 * Method that checks if there is any Token left in the stream with regards
	 * to the current pointer. DOES NOT ADVANCE THE POINTER
	 * 
	 * @return true if at least one Token exists, false otherwise
	 */

	List<Token> tokens;


	int currentIndex;
	boolean isRemoved = false;

	public TokenStream(ArrayList<Token> tokens) {
		this.tokens = tokens;
		currentIndex = 0;
	}

	@Override
	public boolean hasNext() {
		return ((tokens.size() > 0) && !(tokens.size() <= currentIndex));
		// return !(tokens.size() == currentIndex);
	}

	/**
	 * Method to return the next Token in the stream. If a previous hasNext()
	 * call returned true, this method must return a non-null Token. If for any
	 * reason, it is called at the end of the stream, when all tokens have
	 * already been iterated, return null
	 */
	@Override
	public Token next() {
		if (currentIndex == -1)
			currentIndex++;

		if (hasNext()) {
			isRemoved = false;
			return tokens.get(currentIndex++);
		} else {
			if (tokens.size() == currentIndex) {
				currentIndex++;
			}
			return null;
		}
	}

	/**
	 * Method to remove the current Token from the stream. Note that "current"
	 * token refers to the Token just returned by the next method. Must thus be
	 * NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		if (currentIndex > 0 && currentIndex <= tokens.size())
		{
			tokens.remove(--currentIndex);
			isRemoved = true;
		}
	}

	public List<Token> GetBeforeTokens(int width) {
		int startingIndex = (currentIndex - width) <= 0 ? 0 : currentIndex
				- width;
		List surroundingTokens = new ArrayList(tokens.subList(startingIndex,
				currentIndex));
		return surroundingTokens;
	}

	public List<Token> GetAfterTokens(int width) {
		int endingIndex = (currentIndex + width) >= tokens.size() - 1 ? tokens
				.size() - 1 : currentIndex + width;
		List surroundingTokens = new ArrayList(tokens.subList(currentIndex,
				endingIndex));
		return surroundingTokens;
	}

	/**
	 * Method to reset the stream to bring the iterator back to the beginning of
	 * the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		currentIndex = -1;
	}

	/**
	 * Method to append the given TokenStream to the end of the current stream
	 * The append must always occur at the end irrespective of where the
	 * iterator currently stands. After appending, the iterator position must be
	 * unchanged Of course this means if the iterator was at the end of the
	 * stream and a new stream was appended, the iterator hasn't moved but that
	 * is no longer the end of the stream.
	 * 
	 * @param stream
	 *            : The stream to be appended
	 */
	public void append(TokenStream stream) {
		if(null != stream && null != stream.tokens &&  !stream.tokens.isEmpty())
			this.tokens.addAll(stream.tokens);
	}

	/**
	 * Method to get the current Token from the stream without iteration. The
	 * only difference between this method and {@link TokenStream#next()} is
	 * that the latter moves the stream forward, this one does not. Calling this
	 * method multiple times would not alter the return value of
	 * {@link TokenStream#hasNext()}
	 * 
	 * @return The current {@link Token} if one exists, null if end of stream
	 *         has been reached or the current Token was removed
	 */
	public Token getCurrent() {
		if(isRemoved)
			return null;
		else if (currentIndex <= 0 || currentIndex > tokens.size()) {
			return null;
		} else
			return tokens.get(currentIndex - 1);
	}

	public int getSize() {
		return tokens.size();
	}

	public int getIndex() {
		return currentIndex;
	}

	@Override
	public String toString() {
		return "";

	}

	public Token getNextWithoutChangingPtr() {
		if(tokens.size() > currentIndex){
			return tokens.get(currentIndex);
		}
		return null;
	}
	
	public Token getPreviousWithoutChangingPtr() {
		if((currentIndex - 2) >=0) {
			return tokens.get(currentIndex-2);
		}
		return null;
	}




}
