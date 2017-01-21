#include <iostream>
using namespace std;

const int arrLen = 8;
const int seqLen = 8;

bool langfordSequence(int * arr, int indx, int *seq, int pos);
int main() {

    int arr[] = {1,1,2,2,3,3,4,4};
    int seq[] = {0,0,0,0,0,0,0,0};

    bool test = langfordSequence(arr, 0, seq, 0);

    if (test)
        cout << "Langford Sequence Successful: " << endl;
    else
        cout << "Langford Sequence Failed: " << endl;

    for (int i = 0; i < seqLen; i++)
    {
        cout << seq[i] << " ";
    }   
    return 0;
}

bool langfordSequence(int * arr, int indx, int *seq, int pos)
{

    if (indx >= arrLen - 1) //this means we've reached the end of the array
        return true;

    if (pos + arr[indx] + 1 >= seqLen)  //if the second part of the number is off the array
        return false;

    if (seq[pos] == 0 && seq[pos + arr[indx] + 1] == 0)
    {
        seq[pos] = arr[indx];
        seq[pos + arr[indx] + 1] = arr[indx];
        if (langfordSequence(arr, indx + 2, seq, 0))    //the current pair is good, go to the next one, start from the beginning
            return true;
        else
        {
            seq[pos] = 0;
            seq[pos + arr[indx] + 1] = 0;
            if (langfordSequence(arr, indx, seq, pos + 1))
                return true;
        }
    }
    else
    {
        if (langfordSequence(arr, indx, seq, pos + 1))  //current position is no good, try next position
            return true;
    }
}
