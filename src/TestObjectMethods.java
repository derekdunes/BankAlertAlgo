
public class TestObjectMethods {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//double[] amounts = {1000.0,-2000.0,8000.0,-250.0,19800.0,-2000.0,1000.0,-2000.0,-9000.0,-2000.0,5000.0}; 
		//checkMostReoccured(amounts);
		//getBalance(amounts);
		String textMsg[] = {"access)))Credit Alert Amt: NGN 2000.00 Cr Acc: 073****630 Desc: MBAH DEREK ONYEDIKACHI/Online Time: 30/08/17 a02:33 PM Bal: NGN 2,000.00 #SaveToday",
							"Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
							"access>>>Debit Alert Amt: NGN 50.00 Dr Acc: 073****630 Desc: MONTHLY CARD FEE ON:0020120685 Time: 30/08/17 a02:33 PM Bal: NGN 3,000.52 #SaveToday",
							"Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
							"access)))Credit Alert Amt: NGN 2000.00 Cr Acc: 073****630 Desc: MBAH DEREK ONYEDIKACHI/Online Time: 30/08/17 a02:33 PM Bal: NGN 2,000.00 #SaveToday",
							"Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
							"access>>>Debit Alert Amt: NGN 50.00 Dr Acc: 073****630 Desc: MONTHLY CARD FEE ON:0020120685 Time: 30/08/17 a02:33 PM Bal: NGN 3,000.52 #SaveToday",
							"Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
		                    "access>>>Debit Alert Amt: NGN 50.00 Dr Acc: 073****630 Desc: MONTHLY CARD FEE ON:0020120685 Time: 30/08/17 a02:33 PM Bal: NGN 3,000.52 #SaveToday",
		                    "Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
		                    "Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
		                    "Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
		                    "access>>>Debit Alert Amt: NGN 50.00 Dr Acc: 073****630 Desc: MONTHLY CARD FEE ON:0020120685 Time: 30/08/17 a02:33 PM Bal: NGN 3,000.52 #SaveToday",
		                    "Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#",
		                    "access>>>Debit Alert Amt: NGN 50.00 Dr Acc: 073****630 Desc: MONTHLY CARD FEE ON:0020120685 Time: 30/08/17 a02:33 PM Bal: NGN 3,000.52 #SaveToday"
		};
		
		String msgDates[] = {"3434.34", "3434.34", "3434.34", "3434.34", "3434.34",
							"3434.34", "3434.34", "3434.34", "3434.34", "3434.34",
							"3434.34", "3434.34", "3434.34", "3434.34", "3434.34"};
		
		BankMessageToNumbers bm = new BankMessageToNumbers(textMsg, msgDates);
		
		System.out.println(bm.getNoOfTrues());
		System.out.println(bm.getNoOfNoes());
		
		double trAmounts[] = bm.getTransactionAmounts();
		double trDates[] = bm.getTransactionDates();
		double trBalances[] = bm.getBalanceAmounts();
		
		if(trAmounts.length == trDates.length){
			
			for(double s : trAmounts){
				System.out.println(s);
			}
			
			System.out.println();
			
			for(double s : trBalances){
				System.out.println(s);
			}
			
			System.out.println();
			
			for(double s : trDates){
				System.out.println(s);
			}
		}
		
		System.out.println();
		System.out.println(bm.getTotalBalance());
		
	}
	
	public static void checkMostReoccured(double[] mAmount){
		//create a new array to store the no of times a number reoccured
		//get the whole array and loop through the numbers
		//for each number search through array and count how many matches found
		//if found store the no of repetition
		//loop through the no of repetitions
		//for each of repetitions look for other matching repetitions
		//if their amount matches set the new found amount to be (0.0) and 
		//its equivalent no of repetitions as (-1)
		
		int[] NofOccur = new int[mAmount.length];
		int count = 0;
		
		for(int i = 0; i < mAmount.length; i++){
			for(int j = 0; j < mAmount.length; j++){
				
				if(mAmount[i] == mAmount[j]){
					count++;
				}
				
				
				if(j == (mAmount.length-1)){
					NofOccur[i] = count;
					count = 0;
				}
				
				
				
			}

		}
		
		for(int k = 0;k < NofOccur.length; k++){
			int cont = 0;
			for(int l = 0; l < NofOccur.length; l++){
				if(NofOccur[l]== -1 && mAmount[l] == 0.0){
					break;
				}
				if(NofOccur[k] == NofOccur[l] && mAmount[k] == mAmount[l]){
					if(cont != 0){
						NofOccur[l] = -1;
						mAmount[l] = 0.0;
					}
					cont++;
				}
			}
		}
		
		for(int i = 0; i < mAmount.length; i++){
				System.out.println("The No of reOccurances of " + mAmount[i] + " is equal to " + NofOccur[i]);
		}
		
	}
	
	public static void getBalance(double[] transactions){
		double balance = 0.0;
		
		for(int i = 0; i < transactions.length; i++){
			balance = transactions[i] + balance;
			System.out.println(balance);
		}
		
		
		//return balance;
	}
}
