/**
 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 10:59:17$.
 * @since modelo - 2009.
 */
package org.cloudbus.cloudsim.examples.bd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utilitario para tratamento de Numeros.
 * 
 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 10:59:17$.
 * @since modelo - 2009.
 * 
 */
public final class NumberUtils
{

	private static final int INTEGER_MAX_DIGITS = 10;

	public static final int A_MENOR_QUE_B = -1;

	public static final int A_MAIOR_QUE_B = +1;

	public static final int A_IGUAL_B = 0;

	/**
	 * Construtor default private para evitar instancia.
	 * 
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 10:59:18$.
	 * @since modelo - 2009.
	 */
	private NumberUtils()
	{
		super();
	}

	/**
	 * Realiza a comparacao dos valores usando a precisao como parametro.
	 * 
	 * @param a
	 *          Double a
	 * @param b
	 *          Double b
	 * @param scale
	 *          Quantidade de digitos inteiros da operacao.
	 * @param precision
	 *          Numero de casas decimais de precisao
	 * @return -1 se a < b, 0 se a == b, +1 se a > b.
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 11:06:37$.
	 * @since modelo - 2009.
	 */
	public static int compare(final double a, final double b, final int scale,
			final int precision)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumFractionDigits(precision);
		dc.setMinimumFractionDigits(precision);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);
		final String strB = dc.format(b);

		final BigDecimal numberA = new BigDecimal(strA);
		final BigDecimal numberB = new BigDecimal(strB);

		final int compareTo = numberA.compareTo(numberB);

		return compareTo;
	}

	/**
	 * 
	 * Realiza a comparacao dos valores usando a precisao como parametro.
	 * 
	 * @param a
	 *          Numero a
	 * @param b
	 *          Numero b
	 * @param scale
	 *          Quantidade de digitos inteiros da operacao.
	 * @param precision
	 *          Numero de casas decimais de precisao
	 * @return -1 se a < b, 0 se a == b, +1 se a > b.
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 13:25:30$.
	 * @since modelo - 2009.
	 */
	public static int compare(final float a, final float b, final int scale,
			final int precision)
	{
		return NumberUtils.compare((double) a, (double) b, scale, precision);
	}

	/**
	 * 
	 * Realiza a comparacao dos valores usando a precisao como parametro.
	 * 
	 * @param a
	 *          Numero a
	 * @param b
	 *          Numero b
	 * @param scale
	 *          Quantidade de digitos inteiros da operacao.
	 * @return -1 se a < b, 0 se a == b, +1 se a > b.
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 13:25:27$.
	 * @since modelo - 2009.
	 */
	public static int compare(final long a, final long b, final int scale)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);
		final String strB = dc.format(b);

		final BigInteger numberA = new BigInteger(strA);
		final BigInteger numberB = new BigInteger(strB);

		final int compareTo = numberA.compareTo(numberB);

		return compareTo;
	}

	/**
	 * 
	 * Realiza a comparacao dos valores usando a precisao como parametro.
	 * 
	 * @param a
	 *          Numero a
	 * @param b
	 *          Numero b
	 * @param scale
	 *          Quantidade de digitos inteiros da operacao.
	 * @return -1 se a < b, 0 se a == b, +1 se a > b.
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 13:25:24$.
	 * @since modelo - 2009.
	 */
	public static int compare(final int a, final int b, final int scale)
	{
		return NumberUtils.compare((long) a, (long) b, scale);
	}

	/**
	 * 
	 * Realiza a comparacao dos valores usando a precisao como parametro.
	 * 
	 * @param a
	 *          Numero a
	 * @param b
	 *          Numero b
	 * @param scale
	 *          Quantidade de digitos inteiros da operacao.
	 * @return -1 se a < b, 0 se a == b, +1 se a > b.
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 13:25:06$.
	 * @since modelo - 2009.
	 */
	public static int compare(final short a, final short b, final int scale)
	{
		return NumberUtils.compare((long) a, (long) b, scale);
	}

	/**
	 * 
	 * Realiza a comparacao dos valores usando a precisao como parametro.
	 * 
	 * @param a
	 *          Numero a
	 * @param b
	 *          Numero b
	 * @param scale
	 *          Quantidade de digitos inteiros da operacao.
	 * @return -1 se a < b, 0 se a == b, +1 se a > b.
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $13/03/2009 - 13:25:02$.
	 * @since modelo - 2009.
	 */
	public static int compare(final byte a, final byte b, final int scale)
	{
		return NumberUtils.compare((long) a, (long) b, scale);
	}

	public static Double divide(final Number a, final Number b, final int scale,
			final int precision)
	{
		return NumberUtils.div(a, b, scale, precision).doubleValue();
	}

	public static Double divideTruncate(final Number a, final Number b,
			final int scale, final int precision)
	{
		return NumberUtils.div(a, b, scale, precision).doubleValue();
	}

	public static Double multiply(final Number a, final Number b,
			final int scale, final int precision)
	{
		return NumberUtils.mul(a, b, scale, precision).doubleValue();
	}

	public static Double subtract(final Number a, final Number b,
			final int scale, final int precision)
	{
		return NumberUtils.sub(a, b, scale, precision).doubleValue();
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $16/03/2009 - 10:55:03$.
	 * @since modelo - 2009.
	 */
	public static int add(final byte a, final byte b)
	{
		return a + b;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $16/03/2009 - 11:43:38$.
	 * @since modelo - 2009.
	 */
	public static int add(final short a, final short b)
	{
		return a + b;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $16/03/2009 - 11:43:41$.
	 * @since modelo - 2009.
	 */
	public static int add(final int a, final int b)
	{
		return a + b;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $16/03/2009 - 11:43:43$.
	 * @since modelo - 2009.
	 */
	public static long add(final long a, final long b)
	{
		return a + b;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 *          TODO
	 * @param precision
	 *          TODO
	 * @return
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $16/03/2009 - 11:15:33$.
	 * @since modelo - 2009.
	 */
	public static double add(final double a, final double b, final int scale,
			final int precision)
	{
		return NumberUtils.sum(a, b, scale, precision).doubleValue();
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @param precision
	 * @return
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $16/03/2009 - 11:35:48$.
	 * @since modelo - 2009.
	 */
	public static float add(final float a, final float b, final int scale,
			final int precision)
	{
		return NumberUtils.sum(a, b, scale, precision).floatValue();
	}

	/**
	 * <p>
	 * Obtem um interiro apartir de um Numero.
	 * </p>
	 * 
	 * <p>
	 * Caso um Numero em ponto flutuante for informado a precisao sera considerada
	 * como fator de arredondamento para cima ou para baixo. Ex.: 1.1 -> 1; 1.5 ->
	 * 2.
	 * </p>
	 * 
	 * @param number
	 *          Numero que sera convertido para inteiro.
	 * @return O Numero convertido ou null se o Numero informado for null.
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $27/04/2009 - 11:54:22$.
	 * @since modelo - 2009.
	 */
	public static Integer getInt(final Number number)
	{
		if ( number == null )
		{
			return null;
		}

		return NumberUtils.sum(number, 0, NumberUtils.INTEGER_MAX_DIGITS, 0)
				.intValue();
	}

	/**
	 * 
	 * Converte um texto para integer tratando qualquer exce��o
	 * 
	 * @author Humberto Lima [humberto.silva@cagece.com.br] 03/04/2012 - 14:27:32.
	 * @param number
	 * @return o numero convertido, ou zero(0) caso n�o seja um inteiro valido.
	 */
	public static Integer getInt(final String number)
	{
		int numberInteger;
		try
		{
			numberInteger = Integer.parseInt(number);
		}
		catch (final Exception e)
		{
			numberInteger = 0;
		}

		return numberInteger;
	}

	/**
	 * Converte um texto para long tratando qualquer exce��o
	 * 
	 * @author Humberto Lima [humberto.silva@cagece.com.br] 03/04/2012 - 14:27:32.
	 * @param number
	 * @return o numero convertido, ou zero(0) caso n�o seja um inteiro valido.
	 */
	public static Long getLong(final String number)
	{
		long numberLong;

		try
		{
			numberLong = Long.parseLong(number);
		}
		catch (final Exception e)
		{
			numberLong = 0;
		}

		return numberLong;
	}

	/**
	 * Metodo Utilitario para somatorio de valores.
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @param precision
	 * @return
	 * @author F. Assis V. M. Junior[assisprog@gmail.com] $16/03/2009 - 11:34:53$.
	 * @since modelo - 2009.
	 */
	private static BigDecimal sum(final Number a, final Number b,
			final int scale, final int precision)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumFractionDigits(precision);
		dc.setMinimumFractionDigits(precision);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);
		final String strB = dc.format(b);

		final BigDecimal numberA = new BigDecimal(strA);
		final BigDecimal numberB = new BigDecimal(strB);

		final BigDecimal retorno = numberA.add(numberB);

		return retorno;
	}

	private static BigDecimal div(final Number a, final Number b,
			final int scale, final int precision)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumFractionDigits(precision);
		dc.setMinimumFractionDigits(precision);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);
		final String strB = dc.format(b);

		final BigDecimal numberA = new BigDecimal(strA);
		final BigDecimal numberB = new BigDecimal(strB);

		final BigDecimal retorno = numberA.divide(numberB, precision,
				RoundingMode.HALF_UP);

		return retorno;
	}

	/**
	 * Truncar as casas decimais de um valor informado.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] Jul 16, 2010 - 5:23:23 PM.
	 * @param valor
	 *          Valor para truncar.
	 * @param numCasasDecimais
	 *          Cadas decimais.
	 * @return Valor truncado.
	 */
	public static Double truncatarSemArredondar(final Float valor,
			final int numCasasDecimais)
	{
		if ( valor != null )
		{
			final BigDecimal valorEntrada = new BigDecimal(valor);

			final BigDecimal retorno = valorEntrada.divide(new BigDecimal(1),
					numCasasDecimais, RoundingMode.DOWN);

			return retorno.doubleValue();
		}
		return null;
	}

	/**
	 * Truncar as casas decimais de um valor informado.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] Jul 16, 2010 - 5:23:23 PM.
	 * @param valor
	 *          Valor para truncar.
	 * @param numCasasDecimais
	 *          Cadas decimais.
	 * @return Valor truncado.
	 */
	public static Integer dividirArredondarAMaior(final Number a, final Number b,
			final int scale)
	{

		final BigDecimal valor = div(a, b, scale, 10);

		if ( valor != null )
		{
			final BigDecimal retorno = valor.divide(new BigDecimal(1), 0,
					RoundingMode.UP);

			return retorno.intValue();
		}
		return null;
	}

	/**
	 * Truncar as casas decimais de um valor informado.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] Jul 16, 2010 - 5:23:23 PM.
	 * @param valor
	 *          Valor para truncar.
	 * @param numCasasDecimais
	 *          Cadas decimais.
	 * @return Valor truncado.
	 */
	public static Double truncarArredondarAMaior(final Float valor,
			final int numCasasDecimais)
	{
		if ( valor != null )
		{
			final BigDecimal valorEntrada = new BigDecimal(valor);

			final BigDecimal retorno = valorEntrada.divide(new BigDecimal(1),
					numCasasDecimais, RoundingMode.UP);

			return retorno.doubleValue();
		}
		return null;
	}

	/**
	 * Truncar as casas decimais de um valor informado.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] Jul 16, 2010 - 5:23:23 PM.
	 * @param valor
	 *          Valor para truncar.
	 * @param numCasasDecimais
	 *          Cadas decimais.
	 * @return Valor truncado.
	 */
	public static Double truncatarSemArredondar(final Double valor,
			final int numCasasDecimais)
	{
		if ( valor != null )
		{
			final BigDecimal valorEntrada = new BigDecimal(valor);

			final BigDecimal retorno = valorEntrada.divide(new BigDecimal(1),
					numCasasDecimais, RoundingMode.DOWN);

			return retorno.doubleValue();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static BigDecimal divTruncate(final Number a, final Number b,
			final int scale, final int precision)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumFractionDigits(precision);
		dc.setMinimumFractionDigits(precision);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);
		final String strB = dc.format(b);

		final BigDecimal numberA = new BigDecimal(strA);
		final BigDecimal numberB = new BigDecimal(strB);

		return numberA.divide(numberB, precision, RoundingMode.DOWN);
	}

	private static BigDecimal mul(final Number a, final Number b,
			final int scale, final int precision)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumFractionDigits(precision);
		dc.setMinimumFractionDigits(precision);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);
		final String strB = dc.format(b);

		final BigDecimal numberA = new BigDecimal(strA);
		final BigDecimal numberB = new BigDecimal(strB);

		final BigDecimal retorno = numberA.multiply(numberB);

		return retorno;
	}

	/**
	 * Multiplicar por um valor, sem cortar a casas decimais do segundo Numero.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] Jun 14, 2011 - 1:05:57 PM.
	 * @param a
	 * @param b
	 * @param scale
	 * @param precision
	 * @return
	 */
	public static BigDecimal multiplicar(final Number a, final Number b,
			final int scale, final int precision)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumFractionDigits(precision);
		dc.setMinimumFractionDigits(precision);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);

		final BigDecimal numberA = new BigDecimal(strA);
		final BigDecimal numberB = new BigDecimal(b.doubleValue());

		final BigDecimal retorno = numberA.multiply(numberB);

		final int pos = retorno.toString().indexOf(".") == 0 ? retorno.toString()
				.indexOf(",") : retorno.toString().indexOf(".");

		final String valorTruncado = retorno.toString().substring(0,
				pos + precision);

		return new BigDecimal(valorTruncado);
	}

	private static BigDecimal sub(final Number a, final Number b,
			final int scale, final int precision)
	{
		final NumberFormat dc = NumberFormat.getInstance(Locale.US);

		dc.setGroupingUsed(false);

		dc.setMaximumFractionDigits(precision);
		dc.setMinimumFractionDigits(precision);

		dc.setMaximumIntegerDigits(scale);

		final String strA = dc.format(a);
		final String strB = dc.format(b);

		final BigDecimal numberA = new BigDecimal(strA);
		final BigDecimal numberB = new BigDecimal(strB);

		final BigDecimal retorno = numberA.subtract(numberB);

		return retorno;
	}

	/**
	 * Utilizado para formatar o valor em real R$ 00,00
	 * 
	 * @param numero
	 *          Float
	 * @return String formatada
	 * @author Carlos Rafael da C. Miranda[carlorafa@gmail.com] $17/09/2009 -
	 *         09:39:36$.
	 * @since modelo - 2009.
	 */
	public static String formataMoedaReal(final Number numero)
	{
		final Locale brLocale = new Locale("pt", "BR");

		final NumberFormat moeda = NumberFormat.getCurrencyInstance(brLocale);

		return moeda.format(numero);
	}

	/**
	 * Pegar um valor zerado com duas casas decimais para c�lculo de valores.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] - 09/11/2009 - 09:33:00
	 * @return Numero com valor zerado.
	 */
	public static BigDecimal getValorZeradoComDuasCasasDecimais()
	{
		final BigDecimal valorZerado = new BigDecimal(0D);
		valorZerado.setScale(19, 2);

		return valorZerado;
	}

	/**
	 * Pegar um valor zerado com duas casas decimais para c�lculo de valores.
	 * Seta um valor inicial.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] - 09/11/2009 - 09:37:17
	 * @param valor
	 *          Valor a ser inicializado.
	 * 
	 * @return Valor inicializado.
	 */
	public static BigDecimal getValorZeradoComDuasCasasDecimais(final Float valor)
	{
		final BigDecimal valorZerado = new BigDecimal(valor);
		valorZerado.setScale(19, 2);

		return valorZerado;
	}

	/**
	 * Pegar um valor zerado com cinco casas decimais para c�lculo de valores.
	 * Seta um valor inicial.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] - 09/11/2009 - 09:37:17
	 * @param valor
	 *          Valor a ser inicializado.
	 * 
	 * @return Valor inicializado.
	 */
	public static BigDecimal getValorZeradoComCincoCasasDecimais(final Float valor)
	{
		final BigDecimal valorZerado = new BigDecimal(valor);
		valorZerado.setScale(19, 5);

		return valorZerado;
	}

	/**
	 * Pegar um valor zerado com duas casas decimais para c�lculo de valores.
	 * Setando um valor inicial.
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] - 09/11/2009 - 09:37:34
	 * @param valor
	 *          Valor a ser inicializado.
	 * 
	 * @return Valor inicializado.
	 */
	public static BigDecimal getValorZeradoComDuasCasasDecimais(final Double valor)
	{
		final BigDecimal valorZerado = new BigDecimal(valor);
		valorZerado.setScale(19, 2);

		return valorZerado;
	}

	/**
	 * Retorna um valor sem o ponto das casas decimais.
	 * 
	 * @author Dirceu da Silva [dirceusr@gmail.com] - $25/02/2010:10:59:52$
	 * 
	 * @param valor
	 * @return
	 */
	public static Long getValorSemPonto(final Number valor)
	{
		if ( valor != null )
		{
			final String str = String.valueOf(valor);
			final String vlr = str.replace(".", ""); // tira ponto
			return Long.valueOf(vlr);

		}
		return null;

	}

	/**
	 * Retorna um valor sem o ponto das casas decimais.
	 * 
	 * @author Dirceu da Silva [dirceusr@gmail.com] - $25/02/2010:10:59:52$
	 * 
	 * @param valor
	 * @return
	 */
	public static Double getValorSemPontoD(final Number valor)
	{
		if ( valor != null )
		{
			final String str = String.valueOf(valor);
			final String vlr = str.replace(".", ""); // tira ponto
			return Double.valueOf(vlr);

		}
		return null;

	}

	/**
	 * Efetuar o calculo de multiplicacao e divisao de um valor, deixando o
	 * arredondamento parecido com o mainframe(SCI).
	 * 
	 * @author Humberto Lima [humbertolimaa@gmail.com] Jan 18, 2011 - 2:43:41 PM.
	 * @param valorEntrada
	 *          Valor Total para calculo.
	 * @param percentual
	 *          Percentual para multiplicacao.
	 * @return Valor calculado.
	 */
	public static Double calcular(final Double valorEntrada,
			final Double percentual)
	{
		final Double valorDivido = valorEntrada * percentual / 100;

		final String valorEmString = valorDivido.toString();

		int indexOf = valorEmString.indexOf(",");

		if ( indexOf < 0 )
		{
			indexOf = valorEmString.indexOf(".");
		}

		int length = indexOf + 3;

		if ( length > valorEmString.length() )
		{
			length = valorEmString.length();
		}

		final String valorComDuasCasas = valorEmString.substring(0, length);

		final Double valorRetorno = new Double(valorComDuasCasas);

		return valorRetorno;
	}

	public static Double multiplicar(final Double valor1, final Double valor2)
	{
		final BigDecimal valor11 = new BigDecimal(valor1);

		final BigDecimal valor12 = new BigDecimal(valor2);

		final BigDecimal decimal = valor11.multiply(valor12, MathContext.DECIMAL32);

		return decimal.doubleValue();
	}

	public static Double multiplicar(final Double valor1, final Float valor2)
	{
		final BigDecimal valor11 = new BigDecimal(valor1);

		final BigDecimal valor12 = new BigDecimal(valor2);

		final BigDecimal decimal = valor11.multiply(valor12, MathContext.DECIMAL32);

		return decimal.doubleValue();
	}

	public static Double multiplicar(final Float valor1, final Double valor2)
	{
		final BigDecimal valor11 = new BigDecimal(valor1);

		final BigDecimal valor12 = new BigDecimal(valor2);

		// final Double valorDivido = valor1 * valor2;

		final BigDecimal decimal = valor11.multiply(valor12, MathContext.DECIMAL32);

		return decimal.doubleValue();
	}

	public static Double multiplicar(final Integer valor1, final Float valor2)
	{
		final BigDecimal valor11 = new BigDecimal(valor1);

		final BigDecimal valor12 = new BigDecimal(valor2);

		// final Double valorDivido = valor1 * valor2;

		final BigDecimal decimal = valor11.multiply(valor12, MathContext.DECIMAL32);

		return decimal.doubleValue();
	}

	public static void main(final String[] args)
	{
		final Double calcular = multiplicar(10D, 1.28D);

		System.out.println("Valor 3: " + calcular);

		final Double calcular2 = multiplicar(10D, 1.39D);

		System.out.println("Valor 2: " + calcular2);

		final Double calcular4 = multiplicar(10, 1.39F);

		System.out.println("Valor 4: " + calcular4);

		final Double calcular5 = multiplicar(10, 1.28F);

		System.out.println("Valor 5: " + calcular5);

		final Double calcular6 = multiplicar(10F, 1.39D);

		System.out.println("Valor6 : " + calcular6);

		final Double calcular7 = multiplicar(10F, 1.28D);

		System.out.println("Valor 7: " + calcular7);
	}
}
